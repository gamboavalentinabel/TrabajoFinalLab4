<script setup>
  import _post from '../../api/_post.js'
  import _put from '../../api/_put.js'

  import { onUpdated, ref } from 'vue'

  var libro = ref(null)
  var usuario = ref(null)
  var bGuardar = ref(true)
  
  const props = defineProps({
    show: Boolean,
    entity: String,
    modify: Boolean,
    librosDB: Array,
    usuariosDB: Array,
    data: Object({
        idPrestamo: String,
        libro: String,
        usuario: Number
    })
  })

  const emit = defineEmits(['close', 'emitAlertBox'])

  function checkValues(){
    bGuardar.value = 
      libro.value &&
      usuario.value
    ? false: true
  }

  async function prestamoApiRes(){
    let result
    if(props.modify){
      result = await _put(props.entity, 
        {
            idPrestamo: props.data.idPrestamo,
            idLibro: libro.value.idLibro, 
            dniUsuario: usuario.value.dniUsuario
        }
      )
    }else{
      result = await _post(props.entity, 
        {
            idLibro: libro.value.idLibro, 
            dniUsuario: usuario.value.dniUsuario
        }
      )
    }

    if(result) emit('emitAlertBox', result.response)

    emit('close')
  }

  onUpdated(async ()=>{
    if(props.modify){
      const info = props.data

      libro.value = info.libro
      usuario.value = info.usuario
    }else{
      libro.value = null
      usuario.value = null
    }
    
    checkValues()
  })
</script>

<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container">
        <h1>{{ modify ? 'Modificar': 'Nuevo' }} Prestamo:</h1>
        <form>
          <label>Libro: </label>
          <select @click="checkValues" v-model="libro">
              <option v-for="libro_ in librosDB" :key="libro_" :value="libro_" >{{ libro_.titulo }} [Autor: {{ libro_.autor }}]</option>
          </select>
          <label>Usuario: </label>
          <select @click="checkValues" v-model="usuario">
              <option v-for="usuario_ in usuariosDB" :key="usuario_" :value="usuario_">{{ usuario_.nombre }} {{ usuario_.apellido }} [DNI: {{ usuario_.dniUsuario }}]</option>
          </select>
        </form>
        <button
              class="modal-button-cancelar"
              @click="$emit('close')"
          >Cancelar</button>
        <button
            class="modal-button-guardar"
            :disabled="bGuardar"
            @click="prestamoApiRes"
        >Guardar</button>
      </div>
    </div>
  </Transition>
</template>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: grid;
  transition: opacity 0.3s ease;
  color: #000000;
}

.modal-container {
  width: 350px;
  padding: 10px;
  place-self: center;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
}

h1{
  margin: 0 0 5px 0;
  border-bottom: 2px solid #000000;
}

form{
  display: grid;
  row-gap: 5px;
}

input, textarea{
  border: 1px solid gray;
  background-color: #fff;
  color: #000000;
  resize: vertical;
}

.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

button{
  margin-top: 10px;
  float: right;
}

.modal-button-cancelar {
  background-color: rgb(219, 0, 0);
  border-color: rgb(219, 0, 0);
  border-radius: 5px;
}

.modal-button-guardar {
  background-color: rgb(0, 180, 0);
  border-color: rgb(0, 180, 0);
  border-radius: 5px;
  margin-right: 10px;
}

.modal-button-guardar:disabled {
  background-color: rgb(0, 83, 0);
  border-color: rgb(0, 83, 0);
  border-radius: 5px;
  margin-right: 10px;
  cursor: auto;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
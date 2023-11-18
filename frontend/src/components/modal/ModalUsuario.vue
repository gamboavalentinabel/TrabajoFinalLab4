<script setup>
  import _post from '../../api/_post.js'
  import _put from '../../api/_put.js'

  import { onUpdated, ref } from 'vue'

  var dni = ref(0)
  var nombre = ref('')
  var apellido = ref('')
  
  const props = defineProps({
    show: Boolean,
    entity: String,
    modify: Boolean,
    data: Object({
      dniUsuario: String,
      nombre: String,
      apellido: String
    })
  })

  const emit = defineEmits(['close', 'emitAlertBox'])


  var bGuardar = ref(true)

  function checkValues(){
    bGuardar.value = 
        Number.isInteger(dni.value) &&   
        dni.value>0 && 
        nombre.value!='' && 
        apellido.value!=''
    ? false: true
  }

  async function usuarioApiRes(){
    let result
    if(props.modify){
      result = await _put(props.entity, 
        {
            dniUsuario: dni.value,
            nombre: nombre.value, 
            apellido: apellido.value
        }
      )
    }else{
      result = await _post(props.entity, 
        {
            dniUsuario: dni.value,
            nombre: nombre.value, 
            apellido: apellido.value
        }
      )
    }

    if(result) emit('emitAlertBox', result.response)

    emit('close')
  }

  onUpdated(()=>{
    if(props.modify){
      const info = props.data

      dni.value = info.dniUsuario
      nombre.value = info.nombre
      apellido.value = info.apellido
    }else{
        dni.value = ""
        nombre.value = ""
        apellido.value = ""
    }
    
    checkValues()
  })
</script>

<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container">
        <h1>{{ modify ? 'Modificar': 'Nuevo' }} Usuario:</h1>
        <form>
          <label>DNI (sin puntos): </label>
          <input @input="checkValues" v-model="dni" :disabled="modify" type="number">
          <label>Nombre: </label>
          <input  @input="checkValues" v-model="nombre">
          <label>Apellido: </label>
          <input @input="checkValues" v-model="apellido">
        </form>
        <button
              class="modal-button-cancelar"
              @click="$emit('close')"
          >Cancelar</button>
        <button
            class="modal-button-guardar"
            :disabled="bGuardar"
            @click="usuarioApiRes"
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
<script setup>
  import _post from '../../api/_post.js'
  import _put from '../../api/_put.js'

  import { onUpdated, ref } from 'vue'

  var titulo = ref('')
  var autor = ref('')
  var genero = ref('')
  var numPaginas = ref(0)
  var sinopsis = ref('')
  
  const props = defineProps({
    show: Boolean,
    entity: String,
    modify: Boolean,
    data: Object({
      idLibro: String,
      titulo: String,
      autor: String,
      genero: String,
      numPaginas: Number,
      sinopsis: String
    })
  })

  const emit = defineEmits(['close', 'emitAlertBox'])


  var bGuardar = ref(true)

  function checkValues(){
    bGuardar.value = 
      titulo.value!='' && 
      autor.value!='' && 
      autor.value!='' && 
      genero.value!='' && 
      Number.isInteger(numPaginas.value) &&  
      numPaginas.value>0 && 
      sinopsis.value!=''
    ? false: true
  }

  async function libroApiRes(){
    let result
    if(props.modify){
      result = await _put(props.entity, 
        {
          idLibro: props.data.idLibro,
          titulo: titulo.value, 
          autor: autor.value, 
          genero: genero.value, 
          numPaginas: numPaginas.value, 
          sinopsis: sinopsis.value
        }
      )
    }else{
      result = await _post(props.entity, 
        {
          titulo: titulo.value, 
          autor: autor.value, 
          genero: genero.value, 
          numPaginas: numPaginas.value, 
          sinopsis: sinopsis.value
        }
      )
    }

    if(result) emit('emitAlertBox', result.response)

    emit('close')
  }

  onUpdated(()=>{
    if(props.modify){
      const info = props.data

      titulo.value = info.titulo
      autor.value = info.autor
      genero.value = info.genero
      numPaginas.value = info.numPaginas
      sinopsis.value = info.sinopsis

      checkValues()
    }else{
      titulo.value = ""
      autor.value = ""
      genero.value = ""
      numPaginas.value = 0
      sinopsis.value = ""
      
      checkValues()
    }
  })
</script>

<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container">
        <h1>{{ modify ? 'Modificar': 'Nuevo' }} Libro:</h1>
        <form>
          <label>Titulo: </label>
          <input  @input="checkValues" v-model="titulo">
          <label>Autor: </label>
          <input @input="checkValues" v-model="autor">
          <label>Genero: </label>
          <input @input="checkValues" v-model="genero">
          <label>Numero de Paginas: </label>
          <input @input="checkValues" type="number" v-model="numPaginas">
          <label>Sinopsis: </label>
          <textarea @input="checkValues" v-model="sinopsis" rows="5"></textarea>
        </form>
        <button
              class="modal-button-cancelar"
              @click="$emit('close')"
          >Cancelar</button>
        <button
            class="modal-button-guardar"
            :disabled="bGuardar"
            @click="libroApiRes"
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
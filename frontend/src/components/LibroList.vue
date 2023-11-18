<script setup>
  import _getAll from '../api/_getAll.js'
  import _delete from '../api/_delete.js'

  import ModalLibro from './modal/ModalLibro.vue';

  import { ref, onMounted  } from 'vue'
  
  const emit = defineEmits(['emitAlertBox'])

  const showModalLibro = ref(false)
  var tituloFiltro = ref("")
  var autorFiltro = ref("")
  var generoFiltro = ref("")

  var librosDB = ref([])
  var librosTable = ref([])
  var modify = ref(false)
  var data = ref({})

  async function getLibro() {
    const result = await _getAll('libros')

    if(result.success){
      librosDB.value = result.list
      librosTable.value = result.list
    }
    else if(result){
      actionAlertBox(result.response)
    }
  }

  function libroFiltro(){
    if(tituloFiltro.value || autorFiltro.value || generoFiltro.value){

      librosTable.value = librosDB.value.filter((libro)=>{
        return libro.titulo.toLowerCase().includes(tituloFiltro.value.toLowerCase()) &&
          libro.autor.toLowerCase().includes(autorFiltro.value.toLowerCase()) &&
          libro.genero.toLowerCase().includes(generoFiltro.value.toLowerCase())
      })
    }
    else{
      librosTable.value = librosDB.value
    }
  }

  async function deleteLibro(id) {
    if (!confirm(`Estas seguro que quere eliminar el libro.\n=> ID [${id}]`)) return

    const result = await _delete('libro', id)

    if(result) actionAlertBox(result.response)

    if(result.success) getLibro()
  }

  async function actionAlertBox(response){
    emit('emitAlertBox', response)
  }

  onMounted(async () => {
    getLibro()
  })
</script>

<template>
    <h1 class="title">Lista de Libros: 
      <input class="libroFilter" v-model="tituloFiltro" @input="libroFiltro" placeholder="Filtrar por Titulo">
      <input class="libroFilter" v-model="autorFiltro" @input="libroFiltro" placeholder="Filtrar por Autor">
      <input class="libroFilter" v-model="generoFiltro" @input="libroFiltro" placeholder="Filtrar por Genero">
    </h1>
    <table>
      <tr>
        <th>ID</th>
        <th>Titulo</th>
        <th>Autor</th>
        <th>Genero</th>
        <th>Numero de Paginas</th>
        <th>Sinopsis</th>
        <th style="width: 150px;" ></th>
        <th style="width: 100px;" ></th>
      </tr>
      <tr v-for="libro in librosTable" class="item" :key="libro">
        <td>{{ libro.idLibro }}</td>
        <td>{{ libro.titulo }}</td>
        <td>{{ libro.autor }}</td>
        <td>{{ libro.genero }}</td>
        <td>{{ libro.numPaginas }}</td>
        <td>{{ libro.sinopsis }}</td>
        <td><a @click="showModalLibro = true; data=libro; modify=true">Modificar</a></td>
        <td><a class="adelete" @click="deleteLibro(libro.idLibro)">🗑️</a></td>
      </tr>
    </table>
    <h3><a class="aadd" id="show-modal" @click="showModalLibro = true; modify=false">+ Agregar Libro</a></h3>

    <Teleport to="body">
      <ModalLibro :show="showModalLibro" @close="showModalLibro = false; getLibro()"
      @emitAlertBox="(response) => actionAlertBox(response)" entity='libro' :data="data" :modify="modify"></ModalLibro>
    </Teleport>
</template>

<style scoped>
  .libroFilter{
    margin-left: 10px;
    height: 35px;
    width: 300px;
    font-size: 20px;
    background-color: transparent;
    border: 1px solid #303030;
    color: #ffff;
    font-family: GeistMono;
  }

  h3{
    display: flex;
    flex-direction: row-reverse;
  }
  table{
    width: 100%;
    border-collapse: collapse;
  }

  th{
    font-weight: bold;
  }

  td{
    font-weight: lighter;
    text-align: center;
    padding-top: 10px;
    padding-bottom: 10px;
  }
  
  tr:nth-child(even) {background-color: #303030;}

  a{
    padding: 2px 5px 2px 5px;
    font-weight: bold;
    border: 2px solid;
    border-color: #4d4d4d;
    border-radius: 10px;
    cursor: pointer;
  }

  a:hover{
    background-color: #4d4d4d;
  }

  .adelete{
    border-color: #ff0000;
  }
  .adelete:hover{
    background-color: #ff0000;
  }

  .aadd{
    border-color: #008a00;
  }

  .aadd:hover{
    background-color: #008a00;
  }
</style>
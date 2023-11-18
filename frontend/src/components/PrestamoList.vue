<script setup>
  import _getAll from '../api/_getAll.js'
  import _delete from '../api/_delete.js'

  import ModalPrestamo from './modal/ModalPrestamo.vue';

  import { ref, onMounted  } from 'vue'
  
  const emit = defineEmits(['emitAlertBox'])

  const showModalPrestamo = ref(false)
  var usuarioFiltro = ref("")
  var libroFiltro = ref("")

  var prestamosDB = ref([])
  var usuariosDB = ref([])
  var librosDB = ref([])
  var prestamosTable = ref([])
  var modify = ref(false)
  var data = ref({})

  async function setUsuariosDB(){
    const resultUsuarios = await _getAll('usuarios')
    usuariosDB.value = resultUsuarios.success ? resultUsuarios.list : []
    return usuariosDB.value
  }

  async function setLibrosDB(){
    const resultLibros = await _getAll('libros')
    librosDB.value = resultLibros.success ? resultLibros.list : []
    return librosDB.value
  }

  async function getPrestamos() {
    const result = await _getAll('prestamos')

    if(result.success){
        prestamosDB.value = result.list
        prestamosTable.value = result.list
    }
    else if(result){
        actionAlertBox(result.response)
    }
  }

  function getLibroById(idLibro){
    return librosDB.value.find((libro) => libro.idLibro == idLibro)
  }

  function getUsuarioByDNI(dniUsuario){
    return usuariosDB.value.find((usuario) => usuario.dniUsuario == dniUsuario)
  }

  function prestamoFiltro(){
    if(usuarioFiltro.value || libroFiltro.value){
      var libro_
      if(libroFiltro.value){
        libro_ = librosDB.value.find((libro) => libro.titulo.toLowerCase().includes(libroFiltro.value.toLowerCase()))
      }

      prestamosTable.value = prestamosDB.value.filter((prestamo)=>{
          return usuarioFiltro.value ? prestamo.dniUsuario.toString().includes(usuarioFiltro.value) : true
          && libro_ ? prestamo.idLibro == libro_.idLibro : true
      })
    }
    else{
      prestamosTable.value = prestamosDB.value
    }
  }

  async function deletePrestamo(id) {
    if (!confirm(`Estas seguro que quere eliminar el Prestamo.\n=> ID [${id}]`)) return

    const result = await _delete('prestamo', id)

    if(result) actionAlertBox(result.response)

    if(result.success) getPrestamos()
  }

  async function actionAlertBox(response){
    emit('emitAlertBox', response)
  }

  onMounted(async() => {
    await setUsuariosDB()
    await setLibrosDB()
    await getPrestamos()
  })
</script>

<template>
    <h1 class="title">Lista de Prestamos: 
        <input class="prestamoFilter" v-model="libroFiltro" @input="prestamoFiltro" placeholder="Filtrar por Libro(Titulo)">
        <input class="prestamoFilter" v-model="usuarioFiltro" @input="prestamoFiltro" placeholder="Filtrar por Usuario(DNI)">
    </h1>
    <table>
      <tr>
        <th>ID</th>
        <th>Libro (Titulo, Autor)</th>
        <th>Usuario (DNI, Nombre y Apellido)</th>
        <th style="width: 150px;" ></th>
        <th style="width: 100px;" ></th>
      </tr>
      <tr v-for="prestamo in prestamosTable" class="item" :key="prestamo">
        <td>{{ prestamo.idPrestamo }}</td>
        <td>{{ getLibroById(prestamo.idLibro).titulo }}, {{ getLibroById(prestamo.idLibro).autor }}</td>
        <td>{{ prestamo.dniUsuario }}, {{ getUsuarioByDNI(prestamo.dniUsuario).nombre }} {{ getUsuarioByDNI(prestamo.dniUsuario).apellido }}</td>
        <td>
          <a @click="showModalPrestamo = true; 
              data={idPrestamo: prestamo.idPrestamo, libro: getLibroById(prestamo.idLibro), usuario: getUsuarioByDNI(prestamo.dniUsuario)}; 
              modify=true"
          >Modificar</a>
        </td>
        <td><a class="adelete" @click="deletePrestamo(prestamo.idPrestamo)">🗑️</a></td>
      </tr>
    </table>
    <h3><a class="aadd" id="show-modal" @click="showModalPrestamo = true; modify=false">+ Agregar Prestamo</a></h3>

    <Teleport to="body">
      <ModalPrestamo :show="showModalPrestamo" @close="showModalPrestamo = false; getPrestamos()"
      @emitAlertBox="(response) => actionAlertBox(response)" entity='prestamo' :data="data" :modify="modify" :librosDB="librosDB" :usuariosDB="usuariosDB"></ModalPrestamo>
    </Teleport>
</template>

<style scoped>
  .prestamoFilter{
    margin-left: 10px;
    height: 35px;
    width: 400px;
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
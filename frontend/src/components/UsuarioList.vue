<script setup>
  import _getAll from '../api/_getAll.js'
  import _delete from '../api/_delete.js'

  import ModalUsuario from './modal/ModalUsuario.vue';

  import { ref, onMounted  } from 'vue'
  
  const emit = defineEmits(['emitAlertBox'])

  const showModalUsuario = ref(false)
  var nombreFiltro = ref("")
  var apellidoFiltro = ref("")

  var usuariosDB = ref([])
  var usuariosTable = ref([])
  var modify = ref(false)
  var data = ref({})

  async function getUsuarios() {
    const result = await _getAll('usuarios')

    if(result.success){
        usuariosDB.value = result.list
        usuariosTable.value = result.list
    }
    else if(result){
        actionAlertBox(result.response)
    }
  }

  function usuarioFiltro(){
    if(nombreFiltro.value || apellidoFiltro.value){
        usuariosTable.value = usuariosDB.value.filter((usuario)=>{
        return usuario.nombre.toLowerCase().includes(nombreFiltro.value.toLowerCase()) && usuario.apellido.toLowerCase().includes(apellidoFiltro.value.toLowerCase())
      })
    }
    else{
        usuariosTable.value = usuariosDB.value
    }
  }

  async function deleteUsuario(dni) {
    if (!confirm(`Estas seguro que quere eliminar el Usuario.\n=> DNI [${dni}]`)) return

    const result = await _delete('usuario', dni)

    if(result) actionAlertBox(result.response)

    if(result.success) getUsuarios()
  }

  async function actionAlertBox(response){
    emit('emitAlertBox', response)
  }

  onMounted(async () => {
    getUsuarios()
  })
</script>

<template>
    <h1 class="title">Lista de Usuarios: 
        <input class="usuarioFilter" v-model="nombreFiltro" @input="usuarioFiltro" placeholder="Filtrar por Nombre">
        <input class="usuarioFilter" v-model="apellidoFiltro" @input="usuarioFiltro" placeholder="Filtrar por Apellido">
    </h1>
    <table>
      <tr>
        <th>DNI</th>
        <th>Nombre</th>
        <th>Apellido</th>
        <th style="width: 150px;" ></th>
        <th style="width: 100px;" ></th>
      </tr>
      <tr v-for="usuario in usuariosTable" class="item" :key="usuario">
        <td>{{ usuario.dniUsuario }}</td>
        <td>{{ usuario.nombre }}</td>
        <td>{{ usuario.apellido }}</td>
        <td><a @click="showModalUsuario = true; data=usuario; modify=true">Modificar</a></td>
        <td><a class="adelete" @click="deleteUsuario(usuario.dniUsuario)">🗑️</a></td>
      </tr>
    </table>
    <h3><a class="aadd" id="show-modal" @click="showModalUsuario = true; modify=false">+ Agregar Usuario</a></h3>

    <Teleport to="body">
      <ModalUsuario :show="showModalUsuario" @close="showModalUsuario = false; getUsuarios()"
      @emitAlertBox="(response) => actionAlertBox(response)" entity='usuario' :data="data" :modify="modify"></ModalUsuario>
    </Teleport>
</template>

<style scoped>

  .usuarioFilter{
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
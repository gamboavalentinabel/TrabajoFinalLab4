<script setup>
  import { ref, computed } from 'vue'
  import LibroList from './components/LibroList.vue'
  import UsuarioList from './components/UsuarioList.vue';
  import PrestamoList from './components/PrestamoList.vue';
  import NotFound from './components/NotFound.vue'
  import AlertBox from './components/AlertBox.vue';

  const currentPath = ref(window.location.hash)
  const showAlertBox = ref(false)
  var status = ref(0)
  var body = ref("")

  const routes = {
    '/': LibroList,
    '/libro': LibroList,
    '/usuario': UsuarioList,
    '/prestamo': PrestamoList
  } 

  async function actionAlertBox(response){
    status.value = response.status
    body.value = await response.text()
    
    showAlertBox.value = true
  }

  window.addEventListener('hashchange', () => {
    currentPath.value = window.location.hash
  })

  const currentView = computed(() => {
    return routes[currentPath.value.slice(1) || '/'] || NotFound
  })
</script>

<template>
    <header>
      <picture><img alt="Libreria logo" class="logo" src="./assets/logo.png" /></picture>
      <nav>
        <a class="nav-button" :class="{'select': currentPath.includes('usuario')}" href="#/usuario">Usuarios</a>
        <a class="nav-button" :class="{'select': currentPath.includes('prestamo')}" href="#/prestamo">Presatmos</a>
        <a class="nav-button" :class="{'select': currentPath.includes('libro')}" href="#/libro">Libros</a>
      </nav>
    </header>

    <main>
      <component :is="currentView" @emitAlertBox="(response) => actionAlertBox(response)" />

      <Teleport to="body">
        <div class="AlertBoxContainer">
          <AlertBox :show="showAlertBox" @close="showAlertBox = false;" :status="status" :body="body"></AlertBox>
        </div>
      </Teleport>
    </main>
</template>

<style scoped>
.select{
  background-color: gray;
  pointer-events: none;
}

picture{
  display: grid;
  margin-top: 10px;
}
.logo{
  width: 100px;
  place-self: center;
}

nav{
  font-size: 20px;
  font-weight: bold;
  padding: 0;
  margin-top: 20px;
  list-style-type: none;
}

.nav-button{
  border-top: 1px solid gray;
  border-bottom: 1px solid gray;
  height: 50px;
  text-align: center;
  display: grid;
  align-items: center;
  cursor: pointer;
}

.nav-button:hover{
  background-color: gray;
}

.nav-button:first-child{
  border-bottom: 0px;
}

.nav-button:last-child{
  border-top: 0px;
}

.AlertBoxContainer{
    position: absolute;
    top: 25px;
    right: 25px;
    display: grid;
}

</style>

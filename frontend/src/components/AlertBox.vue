<script setup>
  import { ref, reactive, onUpdated } from 'vue'

  // eslint-disable-next-line no-unused-vars
  const props = defineProps({
    show: Boolean,
    status: Number,
    body: String
  })

  const emit = defineEmits(['close'])

  var type = ref("")
  var message = ref("")
  var classObject = reactive({})

  onUpdated(() => {
    if(props.status == 200){
        type.value = "😎OK"
        message.value = props.body
        classObject = reactive({
        'modalOK': true
        })
    } else {
        type.value = "😡Error"
        message.value = props.body
        classObject = reactive({
        'modalError': true
        })
    }

    if(props.show){
      setTimeout(()=>{
        emit("close");
      }, 5000)
    }
  })
  
</script>

<template>
  <Transition name="modalAlertBox">
    <div v-if="show" class="modalAlertBox-mask" :class="classObject">
      <div class="modalAlertBox-content">
          <h1>{{ type }}:</h1>
          <p class="modalAlertBox-message">{{ message }}</p>
      </div>
      <button
            class="modalAlertBox-button-cancelar"
            @click="$emit('close')"
      >X</button>
    </div>
  </Transition>
</template>

<style>
.modalAlertBox-mask {
  width: 400px;
  border-radius: 12px;
  display: grid;
  transition: opacity 0.5s ease;
  color: #000000;
  box-shadow: 0 6px 20px -5px rgba(0, 0, 0, 0.1);
}

.modalError {
  background-color: red;
}

.modalOK {
  background-color: green;
}

.modalAlertBox-container {
  padding: 10px;
  place-self: end;
  border-radius: 5px;
  transition: all 0.5s ease;
}

.modalAlertBox-enter-from {
  opacity: 0;
}

.modalAlertBox-leave-to {
  opacity: 0;
}

.modalAlertBox-message{
    margin-left: 10px;
}

.modalAlertBox-button-cancelar{
  position: absolute;
  margin: 0;
  top: 5px;
  right: 10px;
  background: none;
  border: none;
  font-size: 15px;
  color: #000000;
}

.modalAlertBox-enter-from .modalAlertBox-container,
.modalAlertBox-leave-to .modalAlertBox-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
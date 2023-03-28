<script setup lang="ts">
import { ref, onBeforeMount } from 'vue'
const props = defineProps({
  concept: { type: Object, required: true }
})
</script>
<style lang="scss" scoped>
.box-concept {
  width: 15vw;
  margin: 0.5rem;
  background-repeat: no-repeat;
  background-size: cover;
  opacity: 0.8;
}
</style>
<template>
  
  <div class="box box-concept" :style="{ backgroundImage: `linear-gradient(rgba(0,0,0,0.95), rgba(0,0,0,0.75)), url(${props.concept.image_url})` }">
    <h6 class="title is-6 mb-1 has-text-white">
      <a :href="`${props.concept.ids.wikipedia}`">
        {{ props.concept.display_name }}
      </a>
    </h6>
    <p class="is-7 has-text-white">{{ props.concept.description }}</p>
  </div>
</template>
<script setup lang="ts">
import { ref, onBeforeMount } from 'vue'
const props = defineProps({
  concept: { type: Object, required: true }
})
</script>
<style lang="scss" scoped>
.concept-image {
  background-repeat: no-repeat;
  background-position: center;
}
</style>
<template>
    <div class="background box is-2 column concept-image" :style="{ backgroundImage: `url(${concept.image_url})` }">
      <h3 class="title is-3">{{ concept.name }}</h3>
      <p>{{ concept.description }}</p>
    </div>
</template>
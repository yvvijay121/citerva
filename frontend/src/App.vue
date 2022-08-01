<script setup lang="ts">
    import { computed } from 'vue'
    import { useRouter } from 'vue-router'

    // computed
    const currentRoute = computed(() => {
        const router = useRouter()
        return router.currentRoute.value.name === 'home'
    })
</script>
<style lang="scss">
  .hidden {
    display: none !important;
  }
  #brandimage {
    height: 1em;
    color: blue;
  }
</style>
<template>
  <nav class="level is-transparent is-white px-5 pt-4 is-mobile" :class="{ hidden: currentRoute }">
    <div class="level-left">
      <router-link to="/" class="level-item"
        ><img id="brandimage" :src="require('./assets/image.svg')" /></router-link>
    </div>
    <div class="level-right">
        <router-link to="/about" class="level-item navbar-item">About</router-link>
    </div>
  </nav>
  <router-view/>
</template>
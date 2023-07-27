<script setup lang="ts">
import { computed } from 'vue';
import { useRouter } from 'vue-router';

const currentRoute = computed(() => {
  const router = useRouter();
  return (router.currentRoute.value.name !== 'home');
});
</script>
<style lang="scss" scoped>
.hidden {
  display: none !important;
}

#brandimage {
  height: 1.25em;
}

.has-text-serif {
  font-family: 'Computer Modern Serif', 'Times New Roman', serif;
}

.logo {
  font-family: 'Bruno Ace SC', sans-serif;
  font-size: 2em;
}
</style>
<template>
  <nav class="level is-transparent is-white px-5 pt-4 is-mobile" v-if="currentRoute">
    <div class="level-left">
      <router-link to="/" class="level-item logo">citerva</router-link>
    </div>
    <div class="level-right">
      <router-link to="/about" class="level-item navbar-item">About</router-link>
    </div>
  </nav>
  <router-view />
</template>
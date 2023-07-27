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
  <nav class="level is-transparent is-white mx-5 mt-4 is-mobile" v-if="currentRoute">
    <div class="level-left">
      <router-link to="/" class="level-item logo">citerva</router-link>
    </div>
    <div class="level-right">
      <router-link to="/about" class="level-item navbar-item">About</router-link>
    </div>
  </nav>
  <router-view />
  <footer class="footer" v-if="currentRoute">
    <div class="content has-text-centered mb-2">
      <strong><router-link to="/about">About</router-link></strong> - <strong><a
          href="https://github.com/yvvijay121/citerva">Github</a></strong>
    </div>
    <div class="content has-text-centered">
      <p>
        <strong>Citerva</strong> by <a href="https://github.com/yvvijay121">Yash Vijay</a>. The source code is licensed
        <a href="https://www.mozilla.org/en-US/MPL/2.0/">MPL 2.0</a>. The article data
        is licensed <a href="https://creativecommons.org/publicdomain/zero/1.0/">CC0</a>.
      </p>
    </div>
  </footer>
</template>
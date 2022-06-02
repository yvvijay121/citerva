<template>
  <nav class="navbar is-transparent">
    <div class="navbar-brand">
      <router-link to="/" class="navbar-item"
        ><img
          src="https://bulma.io/images/bulma-logo.png"
          alt="Bulma: a modern CSS framework based on Flexbox"
          width="112"
          height="28"
      /></router-link>
    </div>

    <div id="navbarExampleTransparentExample" class="navbar-menu">
      <div class="navbar-end">
        <router-link to="/about" class="navbar-item">About</router-link>
      </div>
    </div>
  </nav>
  <router-view/>
</template>

<style lang="scss">

</style>

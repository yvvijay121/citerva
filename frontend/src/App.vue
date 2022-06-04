<template>
  <nav class="navbar is-transparent is-white px-4">
    <div class="navbar-brand">
      <router-link to="/" class="navbar-item"
        ><img id="brandimage" :src="require('./assets/image.svg')" /></router-link>
    </div>
    <div class="navbar-menu">
      <div class="navbar-end">
        <router-link to="/about" class="navbar-item">About</router-link>
      </div>
    </div>
  </nav>
  <router-view/>
</template>
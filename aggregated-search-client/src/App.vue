<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style>
body {
  background-color: #f0f2f5;
}
#app {
  //font-family: Avenir, Helvetica, Arial, sans-serif;
  //-webkit-font-smoothing: antialiased;
  //-moz-osx-font-smoothing: grayscale;
  //text-align: center;
  color: #2c3e50;
  padding: 20px;
  margin: 0 auto;
  max-width: 1024px;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
<script setup lang="ts"></script>

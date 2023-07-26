<style lang="scss">
.brandimage {
  height: 1.75em;
  margin: 2em;
}

.logo {
  font-family: 'Bruno Ace SC', sans-serif;
  font-size: 3.5em;
  padding: 0.45em 2em 0.225em 2em;
}
</style>
<template>
  <div class="
      hero
      columns
      is-mobile is-vcentered is-centered is-fullheight
    ">
    <div class="hero-body column is-narrow">
      <div class="box has-text-centered p-5">
        <h1 class="title logo has-text-primary">citerva</h1>
        <div class="field is-grouped">
          <p class="control is-expanded">
            <input class="input" type="text" placeholder="Search for articles ...">
          </p>
          <p class="control">
            <a class="button is-info">
              Search
            </a>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const regex = `(10[.])[0-9]{4}/[()-_.;:/?!@£$%^&*"'><|A-Za-z0-9]+`;
const articleID = ref('');
const typeOfID = ref('DOI');
const router = useRouter();
const validationerror = ref('');

function searchRedirect() {
  let cleanedText = articleID.value.match(regex);
  if (cleanedText === null) {
    validationerror.value = 'Please enter a valid publication identifier.';
  } else {
    router.push('/info/' + cleanedText[0]);
  }
}
</script>

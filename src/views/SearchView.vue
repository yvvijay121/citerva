<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const searchQuery = ref('');
const autocompleteObject = ref({});
const showAutocomplete = ref(false);
const router = useRouter();

async function autocomplete() {
  if (searchQuery.value.length > 3) {
    fetch(`https://api.openalex.org/autocomplete/works?q=${searchQuery.value}`)
      .then((response) => response.json())
      .then((data) => {
        autocompleteObject.value = data.results;
        showAutocomplete.value = true;
      });
  } else {
    autocompleteObject.value = {};
    showAutocomplete.value = false;
  }
}
</script>
<style lang="scss" scoped>
.surrounding-box {
  width: 50vw;
}

// mobile device surrouding box wider
@media screen and (max-width: 768px) {
  .surrounding-box {
    width: 90vw;
  }
}

.logo {
  font-family: 'Bruno Ace SC', sans-serif;
  font-size: max(2.5em, 4vw);
  padding: 0.45em 0 0.225em 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s ease, opacity 0.15s ease;
  max-height: 22.5vh;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  max-height: 0px;
}

.autoCompletePanel {
  height: 22.5vh;
  overflow-y: scroll;
}

.breadcrumb.has-bullet-separator li+li::before {
  font-size: 0.5em;
}
</style>
<template>
  <div class="
      hero
      columns
      is-mobile is-vcentered is-centered is-fullheight
    ">
    <div class="hero-body column is-narrow">
      <div class="box p-5 surrounding-box">
        <h1 class="title logo has-text-info has-text-centered">citerva</h1>
        <div class="field has-addons">
          <p class="control is-expanded">
            <input class="input" type="text" placeholder="Search for articles ..." @input="autocomplete"
              v-model="searchQuery">
          </p>
          <p class="control">
            <a class="button is-info">
              Search
            </a>
          </p>
        </div>
        <Transition name="fade">
          <article class="panel is-primary autoCompletePanel" v-if="showAutocomplete">
            <router-link class="panel-block is-active" v-for="result in (autocompleteObject as Record<string, unknown>[])" :to="'/info/' + (result.external_id as string).split('doi.org/')[1]">
              <span class="panel-icon pr-5 pl-2">
                <i class="fas fa-book" aria-hidden="true"></i>
              </span>
              <span v-html="result.display_name + ' - ' + result.hint" />
            </router-link>
          </article>
        </Transition>
        <div class="notification is-danger">The search bar isn't working yet, but the autocomplete features do. Hang tight!</div>
      </div>
    </div>
    <div class="hero-foot surrounding-box">
      <footer class="footer pt-0">
        <div class="content has-text-centered mb-2">
          <strong><router-link to="/about">About</router-link></strong> - <strong><a href="https://github.com/yvvijay121/citerva">Github</a></strong>
        </div>
        <div class="content has-text-centered">
          <p>
            <strong>Citerva</strong> by <a href="https://github.com/yvvijay121">Yash Vijay</a>. The source code is licensed
            <a href="https://www.mozilla.org/en-US/MPL/2.0/">MPL 2.0</a>. The article data
            is licensed <a href="https://creativecommons.org/publicdomain/zero/1.0/">CC0</a>.
          </p>
        </div>
      </footer>
    </div>
  </div>
</template>
<template>
  <div
    class="
      hero
      columns
      is-mobile is-vcentered is-centered is-fullheight-with-navbar
    "
  >
    <div class="column is-narrow">
      <div class="box has-text-centered">
        <h3 class="title is-3 m-5">CITATURE</h3>
        <div class="field">
          <div class="control">
            <input
              class="input"
              type="text"
              :placeholder="'Find an article by ' + typeOfID"
              v-model="articleID"
              @keyup.enter="searchRedirect()"
            />
          </div>
        </div>
        <div class="field has-addons">
          <div class="control is-expanded">
            <div class="select is-fullwidth">
              <select v-model="typeOfID">
                <option disabled value="">Please select one</option>
                <option>DOI</option>
                <option>Pubmed</option>
                <option>PMC</option>
              </select>
            </div>
          </div>
          <div class="control">
            <button class="button is-primary" @click="searchRedirect()">
              Submit
            </button>
          </div>
        </div>
      </div>
      <article
        class="message is-danger box p-0"
        :style="{ visibility: validationerror === '' ? 'hidden' : 'visible' }"
      >
        <div class="message-body">
          {{ validationerror }}
        </div>
      </article>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const regex = /(10\.[0-9a-zA-Z]+\/(?:(?!["&\'])\S)+)\b/;
const articleID = ref('');
const typeOfID = ref('DOI');
const router = useRouter();
const validationerror = ref('');

function searchRedirect() {
  let cleanedText = articleID.value.match(regex);
  if (cleanedText === null) {
    validationerror.value = 'Please enter a valid publication identifier.';
  } else {
    router.push('/info/' + articleID.value);
  }
}
</script>

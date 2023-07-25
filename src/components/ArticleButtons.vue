<script setup lang="ts">
import { ref } from 'vue';
const props = defineProps({
    doi: { type: String, required: true }
});
const open_access: any = ref({});

function capitalizeFirstLetter(str: string) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

fetch(`https://api.unpaywall.org/v2/${props.doi}?email=yvvijay121@gmail.com`)
    .then(res => res.json())
    .then(json => {
        open_access.value = json;
    });
</script>
<style lang="scss" scoped></style>
<template>
    <article class="box p-0 message is-success">
        <div class="message-header">
            <p class="capitalize">Open Access Status: {{ capitalizeFirstLetter(open_access.oa_status ?? 'Unknown') }}</p>
        </div>
        <div class="message-body" v-if="open_access.is_oa">
            <div class="content">
                <a class="button is-danger m-1"
                    :href="open_access.best_oa_location.url_for_pdf ?? open_access.first_oa_location.url_for_pdf">
                    <span class="icon is-small">
                        <i class="fas fa-file-pdf"></i>
                    </span>
                    <span>Article PDF</span>
                </a>
                <a class="button is-primary m-1"
                    :href="open_access.best_oa_location.url_for_landing_page ?? open_access.first_oa_location.url_for_landing_page">
                    <span class="icon is-small">
                        <i class="fas fa-file-alt"></i>
                    </span>
                    <span>Full Text</span>
                </a>
            </div>
        </div>
    </article>
</template>
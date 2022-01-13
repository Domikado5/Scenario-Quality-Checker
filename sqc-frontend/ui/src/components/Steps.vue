<template>
    <div class="row mx-1">
        <div class="mb-3 row align-items-center gx-2 gy-3" v-if="root == true">
            <div class="col-lg-2 col-12">
                <label for="keywordInput" class="form-label">Step Keyword</label>
                <input type="text" id="keywordInput" v-model="keyword" class="form-control">
            </div>
            <div class="col-lg-9 col-12">
                <label for="contentInput" class="form-label">Step Content</label>
                <input type="text" id="contentInput" v-model="content" class="form-control">
            </div>
            <div class="col-lg-1 col-12 d-flex justify-content-center align-items-center">
                <button @click="addStep" class="btn btn-outline-success"><i class="bi bi-plus-circle-fill"></i></button>
            </div>
        </div>
        <div class="mb-3 col-12">
            <div v-for="(step, idx) in steps" :key="step.content" class="row mb-1 border border-success align-items-center py-2">
                <div class="col-12 col-lg-10 text-start">{{ step.keyword }}{{ step.keyword.length > 0 ? ' : ' : "" }}{{ step.content }}</div>
                <div class="col-12 col-lg-2 d-flex justify-content-center align-items-center mb-2">
                    <button @click="resetStepForm" data-bs-toggle="modal" :data-bs-target="'#stepModal' + idx + '-' + depthValue" class="btn btn-outline-info me-2"><i class="bi bi-plus-circle-fill"></i></button>
                    <div class="modal fade" :id="'stepModal' + idx + '-' + depthValue" tabindex="-1" :aria-labelledby="'stepModalLabel' + idx + '-' + depthValue" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" :id="'stepModalLabel' + idx + '-' + depthValue">Add step</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <h4>Sub scenario for {{ step.content }}</h4>
                                    <form @submit.prevent="">
                                        <div class="mb-3">
                                            <label for="keywordInput" class="form-label">Step Keyword</label>
                                            <input type="text" id="keywordInput" v-model="keyword" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label for="contentInput" class="form-label">Step Content</label>
                                            <input type="text" id="contentInput" v-model="content" class="form-control">
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button @click="addSubscenario(idx)" type="button" class="btn btn-primary">Save changes</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button @click="removeStep(idx)" class="btn btn-outline-danger"><i class="bi bi-trash-fill"></i></button>
                </div>
                <steps :value="step.subscenario" :root="false" class="col-12"></steps>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Steps",
    props: {
        value: Array,
        root: Boolean,
        depthValue: Number
    },
    data(){
        return {
            steps: this.value,
            keyword: "",
            content: "",
            subscenario: [],
            depth: this.depthValue
        }
    },
    methods: {
        addStep(){
            const step = {
                keyword: this.keyword,
                content: this.content,
                subscenario: []
            }
            this.steps.push(step)
            this.resetStepForm()
        },
        removeStep(idx){
            this.steps.splice(idx, 1)
        },
        resetStepForm(){
            this.keyword = ""
            this.content = ""
            this.subscenario = []
        },
        addSubscenario(idx){
            const step = {
                keyword: this.keyword,
                content: this.content,
                subscenario: []
            }
            this.steps[idx].subscenario.push(step)
            this.resetStepForm()
            document.querySelector("#stepModalLabel" + idx + "-" + this.depthValue + " + button").click()
        },
        submitForm(){
            this.$emit('inputStep', this.steps)
        }
    }
}
</script>
<script setup>
import { ref, watch, reactive, onMounted} from "vue"
import request from "@/utils/request";
import { sparkAddSession, sparkGetSession, sparkUpdateSession} from "@/api/spark"
import { Memo } from '@element-plus/icons-vue'


let article = ref("")
let text = ref("")
let list = ref([]);
let currId = ref(null) 
let session = reactive({
    content: [],
})
let container = ref()
let session_history = ref([])

onMounted(() => {
    sparkGetSession().then(res => {
        console.log(res)
        session_history.value = res.data
    })
})

watch(()=>session.content, () => {
    console.log(111111111111)
    sparkUpdateSession(currId.value,session.content)
},{deep: true})

function spkapi(mes){
    return request.post('/ai/chat', mes)
}
const sse = new EventSource('/api/ai/chat-sse');
sse.addEventListener('message', handleSSEMessage);

function handleSSEMessage(event) {
    if (event.data === 'data: [DONE]') {
        console.log('结束了');
        if (list.value.length === 0 || list.value[list.value.length - 1].value !== text.value) {
            list.value.push({
                value: text.value,
                type: 1
            });
        }
        session.content.push({role: "system", content: text.value})
        scrollToBottom(container.value);
        text.value = '';
    } else {
        console.log(event.data);
        let jsonStartIndex = event.data.indexOf("data: ") + 6;
        let flowText = JSON.parse(event.data.substring(jsonStartIndex))['choices'][0]["delta"]["content"];
        text.value += flowText;
        updateChatDisplay();
    }
}

function updateChatDisplay() {
    if (list.value.length > 0 && list.value[list.value.length - 1].type === 1) {
        list.value[list.value.length - 1].value = text.value;
    } else {
        list.value.push({
            value: text.value,
            type: 1
        });
    }
    scrollToBottom(container.value);
}

async function run() {
    const prompt = article.value
    article.value = ""
    list.value.push({
        value: prompt,
        type: 0
    })
    session.content.push({role: "user", content: prompt})
    scrollToBottom(container.value)
    spkapi(session.content)
}
function scrollToBottom(div) {
    // console.log(1)
    div.scrollTop = div.scrollHeight;
}

function lookBack(item){
    currId.value = item.id
    let content = JSON.parse(item.content)
    session.content = content
    let arr = []
    for(let i=0;i<content.length;i++){
        arr.push({
            value: content[i].content,
            type: content[i].role === "user"? 0 : 1
        })
    }
    list.value = arr
}

function newTalk(){
    session.content = []
    list.value = []
    currId.value = null
    sparkAddSession(session)
    currId.value = session_history.value.length + 1
}


</script>

<template>
    <el-card class="page-container">
        <div class="enter">
            <el-input v-model="article"  @keydown.enter="run" placeholder="Enter 发送" type="textarea" class="input" />
            <el-button type="primary" plain @click="run">发送</el-button>
            <el-button type="primary" plain @click="newTalk">新增</el-button>
        </div>
        <el-dropdown style="position: absolute; ">
            <el-button style="width: 40px; height:40px; background-color: #F3F6FC; border-radius: 30%;">
            <el-icon style="width: 30px; height:30px;"><Memo style="width: 30px; height:30px;"/></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu v-for="item in session_history" :key="item.id" @click="lookBack(item)">
                <el-dropdown-item>{{item.title}}</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        <div class="container" ref="container">
            <template v-for="item in list">

                <div class="left" v-if="item.type === 0" v-bind:key="item.value">
                    <el-card shadow="always">
                        <el-avatar> user </el-avatar> {{ item.value }}
                    </el-card>
                </div>
                <div class="right" v-else v-bind:key="item">
                    <el-card shadow="always">
                        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />{{ item.value
                        }}
                    </el-card>
                </div>
            </template>
        </div>

    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    height: 100%;
    box-sizing: border-box;
}

.container {
    display: flex;
    flex-direction: column;
    height: 52vh;
    overflow-y: scroll;
    background-color: #F3F6FC;
    border-radius: 5%;
    padding-top: 5%;
    padding-bottom: 5%;
}

.left {
    align-self: flex-start;
    border: 2px solid rgb(206, 206, 243);
    width: 60vw;
    border-radius: 4%;
    margin-bottom: 4vh;
}

.right {
    align-self: flex-end;
    border: 2px solid rgb(17, 14, 16);
    width: 60vw;
    border-radius: 4%;
    margin-bottom: 4vh;
}

.input {
    border-radius: 2px, 0px, 0px, 2px;
    width: 70vw;
}

.el-button {
    height: 51.98px;
    width: 5vw;
    border-radius: 0px, 2px, 2px, 0px;
}

.enter {
    display: flex;
    justify-content: center;
    position: fixed;
    top: 82vh;
}
</style>
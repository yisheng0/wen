import {defineStore} from "pinia"
import {ref} from 'vue'
/* 
    第一个参数:名字,唯一性
    第二个参数:函数,函数的内部可以定义状态的所有内容

    返回值: 函数
*/

export let useTokenStore = defineStore('token', ()=>{
    let token = ref('');

    let setToken = (newToken)=>{
        token.value = newToken
    }

    let removeToken = ()=>{
        token.value = ''
    }

    return { token, setToken, removeToken}
},{
    //持久化存储
    presist: true,
})
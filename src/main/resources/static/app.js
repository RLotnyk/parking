const routes=[
    {path:'/car', component:car},
    {path:'/place', component:place}
]

const router=new VueRouter({
    routes
})

const app = new Vue({
    router
}).$mount('#app')
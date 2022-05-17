const base = {
    get() {
        return {
            url : "http://localhost:8080/ssm5kk10/",
            name: "ssm5kk10",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/ssm5kk10/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "中外电影赏析网站"
        } 
    }
}
export default base

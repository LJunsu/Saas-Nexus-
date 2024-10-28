const FetchUtils = {
    fetchForText : (url, options)=>{
        return fetch(url, options)
        .then(resp=>{
            if(resp.ok) {
                return resp.text();
            } else {
                throw new Error(`상태 코드 : ${resp.status}, ${resp.statusText}`);
            }
        })
        .catch(err=>{
            console.log(err);
        });
    },
    fetchForJSON : (url, options)=>{
        return fetch(url, options)
        .then(resp=>{
            if(resp.ok) {
                return resp.json();
            } else {
                throw new Error(`상태 코드 : ${resp.status}, ${resp.statusText}`);
            }
        })
        .catch(err=>{
            console.log(err);
        });
    }
}

// FetchUtils.fetchForText("주소", {옵션 객체}).then(txt=>console.log(txt))
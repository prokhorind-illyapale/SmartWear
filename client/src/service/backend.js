const url = 'http://localhost:8080';

let token = localStorage.token;

const headers = {
  'Accept': 'application/json',
  'Authorization': `Bearer ${token}`
    'Content-Type': 'application/json',
    'X-Requested-With': 'XMLHttpRequest'
};

export const getJson = () => {
    return fetch(`${url}/hello-world`, {headers})
        .then(res => {
             return res.json()
        })
        .then(data => {
            console.log(data);
            return data.message;

        });
};

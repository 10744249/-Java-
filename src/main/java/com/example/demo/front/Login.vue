<template>
  <div class="login">
    <h2>登入</h2>
    <form @submit.prevent="onLogin">
      <div>
        <label for="phoneNumber">手機號碼:</label>
        <input v-model="loginInfo.phoneNumber" type="text" id="phoneNumber" required>
      </div>
      <div>
        <label for="password">密碼:</label>
        <input v-model="loginInfo.password" type="password" id="password" required>
      </div>
      <button type="submit">登入</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      loginInfo: {
        phoneNumber: '',
        password: ''
      }
    };
  },
  methods: {
    onLogin() {
      axios.post('/api/login', this.loginInfo)
          .then(response => {
           
            console.log("登入成功: ", response);
            
            localStorage.setItem("token", response.data.token);
            this.$router.push('/home'); //0317
          })
          .catch(error => {
            console.error("登入失敗: ", error.response);
            alert("登入失敗：" + error.response.data.message);
          });
    }
  }
};
</script>

<style>

.login form {

}
.login form div {
  margin-bottom: 10px;
}
</style>

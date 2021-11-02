# MusicPlayer

This is based on Android music App ,Use MediaPlayer to implement local music playback,Still need to improve,we welcome your comments and ideas.

## ScrenShots

#### LoginActivity

<img src="https://img14.360buyimg.com/ddimg/jfs/t1/169568/27/22560/534547/6180cad6Ef97f783d/eb7a662d45fe753d.jpg" width="150px" height="300px">

#### Playlist

<img src="https://img11.360buyimg.com/ddimg/jfs/t1/210897/30/7657/700592/618104a1E628c9f93/f345a113b43f8265.jpg" width="150px" height="300px">

#### home

<img src="https://img14.360buyimg.com/ddimg/jfs/t1/138644/8/21845/929974/618109e2Ed338c0f6/82e65647892448c0.jpg" width="150px" height="300px">

#### MyLove

<img src="https://img12.360buyimg.com/ddimg/jfs/t1/140618/12/21776/842585/61812fc0E825d61b7/521630215bd91ef1.jpg" width="150px" height="300px">

#### Play interface

<img src="https://img11.360buyimg.com/ddimg/jfs/t1/219677/15/2861/701629/618135d0Eb5a801ca/66a40fb8a4889d44.jpg" width="150px" height="300">



## FEATURES

- Simple background color as theme 

- Add biometric fingerprint login to the login screen

- List interface adopts ListView to implement the playlist

- The home page joins the current mainstream App interface

- Play a preview of the song Audio visualization progress bar

  

  

  ## Implementing Fingerprint Login

  ```java
  //使用ImageButton按钮实现指纹登录的图标
  imageButton1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  BiometricPrompt.PromptInfo promptInfo =
                          new BiometricPrompt.PromptInfo.Builder()
                                  .setTitle("指纹登录")
                                  .setSubtitle("Login using your biometric credential")
                                  .setNegativeButtonText("Cancel")
                                  .build();
  
                  getPrompt().authenticate(promptInfo);
              }
          });
  
   	 private BiometricPrompt getPrompt() {
          Executor exeCutor = ContextCompat.getMainExecutor(this);
          BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
              @Override
              public void onAuthenticationError(int errorCode, CharSequence errString) {
                  super.onAuthenticationError(errorCode, errString);
                  notifyUser(errString.toString());
              }
  
              @Override
              public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                  super.onAuthenticationSucceeded(result);
                  notifyUser("Authentication Succeeded!");
                  Intent intent = new Intent(LoginActivity.this, Home.class);
                  startActivity(intent);
              }
  
              @Override
              public void onAuthenticationFailed() {
                  super.onAuthenticationFailed();
                  notifyUser("Authentication Failed!");
              }
          };
  
          BiometricPrompt biometricPrompt = new BiometricPrompt(this, exeCutor, callback);
          return biometricPrompt;
  
      }
   	private void notifyUser (String message){
          Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
      }
  ```

  
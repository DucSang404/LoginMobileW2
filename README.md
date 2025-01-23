# Hướng dẫn sử dụng dự án

## Thành viên tham gia
- **Phạm Tiến Anh - 22110282**
- **Nguyễn Đức Sang - 22110404**

---

## 1. Mô tả dự án

Dự án cung cấp các chức năng:
- **Đăng ký tài khoản**: Gửi OTP qua email để kích hoạt tài khoản.
- **Đăng nhập**: Sử dụng email và mật khẩu.
- **Quên mật khẩu**: Gửi OTP qua email để đặt lại mật khẩu.

Công nghệ sử dụng:
- **Android (Java)**: Giao diện người dùng, sử dụng thư viện **Retrofit** để gọi API.
- **Spring Boot**: Xây dựng API backend.
- **MySQL**: Quản lý cơ sở dữ liệu.

---

## 2. Triển khai ứng dụng

### a. Cấu hình Backend
1. Sử dụng **Spring Boot** và **MySQL** để triển khai backend.
2. Hình ảnh minh họa:
   - **Spring Boot**:
     ![Spring Boot](image/springboot.png)
   - **MySQL**:
     ![MySQL](image/mysql.png)
3. Mã nguồn backend được lưu trong folder `demo`:
   ![Folder Backend](image/image.png)

---

### b. Cấu hình Android
1. Thêm thư viện Retrofit vào file `build.gradle`:
   ```groovy
   implementation libs.gson
   implementation libs.retrofit
   implementation libs.converter.gson
   ```
2. Cấu hình file `AccountAPI.java`:
   ```java
   public interface AccountAPI {
       @POST("/account/login")
       Call<AccountDTO> login(@Body AccountDTO accountDTO);

       @POST("/account/register")
       Call<MessageDTO> register(@Body RegisterDTO registerDTO);

       @POST("/account/check-user")
       Call<MessageDTO> checkUser(@Body String email);

       @FormUrlEncoded
       @POST("/account/send-otp-for-register")
       Call<MessageDTO> sendOtpForRegister(@Field("email") String email);

       @FormUrlEncoded
       @POST("/account/send-otp")
       Call<Void> sendOtp(@Field("email") String email);

       @POST("/account/verify-otp")
       Call<Void> verifyOtp(@Query("email") String email, @Query("otp") String otp);

       @FormUrlEncoded
       @POST("/account/reset-pass")
       Call<Void> resetPass(@Field("email") String email, @Field("password") String password);
   }
   ```
3. Cấu hình file `RetrofitClient.java`:
   ```java
   public class RetrofitClient {

       private static Retrofit retrofit;
       private static final String BASE_URL = "http://10.0.2.2:8080"; // Thay thế với URL của Spring Boot API

       public static Retrofit getRetrofitInstance() {
           if (retrofit == null) {
               retrofit = new Retrofit.Builder()
                       .baseUrl(BASE_URL)
                       .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON sang đối tượng
                       .build();
           }
           return retrofit;
       }
   }
   ```
4. Sử dụng Retrofit để gọi API từ giao diện người dùng.

---

## 3. Tính năng demo
- **Đăng ký tài khoản**:
  - Nhập email và mật khẩu.
  -  ![image](https://github.com/user-attachments/assets/785c8f7a-f470-433e-b4d5-f475fe4c6742)

  - Gửi OTP đến email, sau đó xác thực để kích hoạt tài khoản.
  - ![image](https://github.com/user-attachments/assets/aa30f201-2103-4bf4-a89e-3b4f554d73db)
  - ![image](https://github.com/user-attachments/assets/d1922b8e-e88a-4b78-a97d-7edd22e6f719)



- **Đăng nhập**:
  - Sử dụng email và mật khẩu đã đăng ký để đăng nhập.
  - ![image](https://github.com/user-attachments/assets/9738eff7-7d9d-4fb1-b536-405d4945db22)
  - Đăng nhập thành công :
  - ![image](https://github.com/user-attachments/assets/352f3594-9818-4fdf-b8b5-705083cbec34)


- **Quên mật khẩu**:
  - Nhập email, nhận OTP qua email, sau đó đặt lại mật khẩu mới.
  - ![image](https://github.com/user-attachments/assets/c10d35ee-5731-466b-8a92-25b81a1ba1d2)
  - ![image](https://github.com/user-attachments/assets/7bc1e8e6-6ab5-4181-a1d7-77b937ee4577)
  - ![image](https://github.com/user-attachments/assets/e0000ffb-fd0e-49b0-a771-dae938331cda)
  - ![image](https://github.com/user-attachments/assets/f235e90c-9bdf-46ff-a2f5-c1c215dc8aa9)


   
---



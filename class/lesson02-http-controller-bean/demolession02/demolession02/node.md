1.Sự khác nhau giữa PUT và PATCH? Khi nào dùng cái nào?
2.HTTP Status Code 401 và 403 khác nhau thế nào? Cho ví dụ thực tế.
3.@Controller và @RestController khác nhau ở điểm nào? Khi nào dùng cái nào?
4.So sánh @PathVariable, @RequestParam, @ModelAttribute, @RequestBody — cơ chế nào là web data binding, cơ chế nào là body deserialization?
5.Tại sao cần có @Service, @Repository trong khi @Component đã đủ?
6.Khi nào dùng @Bean thay cho @Component?
7.Singleton scope có vấn đề gì khi xử lý concurrent request?
8.@PostConstruct khác gì so với viết code trong constructor?

                        TRA LOI

1.PUT là update tất cả các trường
PATCH là update những trường nào ở trong @RequestBody
2.401:chưa xác thực
403:không có quyền
VD: Khi đăng nhập hệ thống sẽ tự gen ra 1 token bao gồm header (Loại token, hàm băm),
payload(exp), chữ ký
Lỗi 401 khi không có token hoặc token hết hạn
Lỗi 403: Khi role user đăng nhập vào trang admin
--> 403 (Khong co quyen truy cap vao tai nguyen-resource)
3.Controller: trả về view, sử dụng khi làm việc với html (theo mô hình String MVC)
RestController: sẽ trả về mã JSON
4.@PathVariable: doc du lieu trong {}
@RequestParam: đọc sau dấu ? -> hay dùng cho filter
@RequestBody: Spring dùng HttpMessageConverter để đọc body và giaải mã sang JSON (Jackson)
@ModelAttribute: lấy dữ liệu từ form, Spring sẽ bind các trường của form sang
thuộc tính của Object
Data binding (ràng buoc du du lieu): tu dong gan cac gia tri tu HTML(Form) vao
cac thuoc tinh cua Object (Path,Param,model)
body deserialization: là quá trình chuyển đổi một chuỗi dữ liệu (thường là định dạng JSON hoặc XML) từ trong thân (Body) của HTTP Request thành một đối tượng Java hoàn chỉnh.
(RequestBody)
5.Chi tiết hơn, tường minh hơn.
Repository: Spring hỗ tro xu ly loi database
6.Bean khi cần sử dụng thư viện thứ 3 và hay ở trong Configuration
Còn Component sẽ không sử dụng được thư viện bên thứ 3
7.concurrent request: la cac yeu cau cung mot khoang thoi gian
Vấn đề: các request sẽ dùng chung instance -> ghi de cac du liệu lên nhau 
8.Đặc điểm,Constructor,@PostConstruct
Thời điểm chạy,"Đầu tiên, khi Object vừa được new.",Sau khi Constructor xong và Dependency được tiêm vào.
Các @Autowired,Chưa có (bằng null).,Đã sẵn sàng để sử dụng.
Mục đích chính,Khởi tạo các giá trị cơ bản nội bộ của Class.,Khởi tạo logic có liên quan đến các Bean khác hoặc DB.
Số lần chạy,Duy nhất 1 lần.,Duy nhất 1 lần.

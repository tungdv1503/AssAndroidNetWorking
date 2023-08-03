<?php
$servername = "localhost";
$username = "id20973106_tungdvph25579"; // Thay thế bằng tên đăng nhập của bạn
$password = "@Tung1503"; // Thay thế bằng mật khẩu của bạn
$dbname = "id20973106_assignment"; 

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Kết nối thất bại: " . $conn->connect_error);
   
}
// Kiểm tra xem yêu cầu đến có phải là POST không
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Lấy thông tin đăng nhập từ ứng dụng Android
    $username = $_POST["Username"];
    $password = $_POST["Password"];

    // Truy vấn cơ sở dữ liệu để kiểm tra thông tin đăng nhập
    $sql = "SELECT * FROM Users WHERE Username = '$username' AND Password = '$password'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        // Đăng nhập thành công, lấy thông tin người dùng từ cơ sở dữ liệu
        $user = $result->fetch_assoc();

        // Tạo mảng phản hồi chứa thông tin người dùng
        $response["success"] = 1;
        $response["message"] = "Đăng nhập thành công";
        $response["user"] = $user;

        // Thiết lập header của phản hồi là JSON
        header('Content-Type: application/json');

        // Chuyển đổi mảng phản hồi thành chuỗi JSON và gửi về cho ứng dụng Android
        echo json_encode($response);
    } else {
        // Đăng nhập thất bại, trả về phản hồi với trường "success" = 0 và thông báo thất bại
        $response["success"] = 0;
        $response["message"] = "Đăng nhập thất bại";

        // Thiết lập header của phản hồi là JSON
        header('Content-Type: application/json');

        // Chuyển đổi mảng phản hồi thành chuỗi JSON và gửi về cho ứng dụng Android
        echo json_encode($response);
    }
} else {
    // Yêu cầu không hợp lệ, trả về phản hồi với trường "success" = 0 và thông báo lỗi
    $response["success"] = 0;
    $response["message"] = "Yêu cầu không hợp lệ";

    // Thiết lập header của phản hồi là JSON
    header('Content-Type: application/json');

    // Chuyển đổi mảng phản hồi thành chuỗi JSON và gửi về cho ứng dụng Android
    echo json_encode($response);
}

// Đóng kết nối cơ sở dữ liệu
$conn->close();
?>

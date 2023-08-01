<?php
$response = array();
$server = "localhost";
$u = "id20973106_tungdvph25579";
$p = "@Tung1503";
$db = "id20973106_assignment";
$conn = new mysqli($server, $u, $p, $db);
if ($conn->connect_error) {
    die("Kết nối lỗi" . $conn->connect_error);
}

if (isset($_POST['Username'])) {
    $Username = $_POST['Username'];
    $sql = "SELECT * FROM Users WHERE Username = '$Username'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        $response["exists"] = true;
    } else {
        $response["exists"] = false;
    }
    echo json_encode($response);
}
?>

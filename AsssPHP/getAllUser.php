<?php
$server = "localhost";  
$u = "id20973106_tungdvph25579"; 
$p = "@Tung1503"; 
$db = "id20973106_assignment"; 
$conn = new mysqli($server, $u, $p, $db);
if ($conn->connect_error) {
    die("Error connecting to database: " . $conn->connect_error);
}
$sql = "SELECT * FROM Users";
$result = $conn->query($sql);

if ($result->num_rows > 0) { 
    $rows = array();
    while ($row = $result->fetch_assoc()) {
        $rows[] = $row;
    }
    $json = json_encode($rows);
    echo '{"Users":' . $json . '}';
} else {
    echo "No users found.";
}

$conn->close();
?>
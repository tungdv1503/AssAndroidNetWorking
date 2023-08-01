<?php
$response = array();
$server="localhost";
$u="id20973106_tungdvph25579";
$p="@Tung1503";
$db="id20973106_assignment";
$conn = new mysqli($server,$u,$p,$db);
if($conn -> connect_error){
    die("Ket noi loi". $conn->connect_error);
}
if(isset($_GET['Id'])&&isset($_GET['Username'])&&isset($_GET['Password'])&&isset($_GET['Email'])){
    $Id = $_GET['Id'];
    $Username = $_GET['Username'];
    $Password = $_GET['Password'];
    $Email = $_GET['Email'];
    $sql = "INSERT INTO Users(MaSV,HoTen,NamSinh) VALUES('$Id','$Username','$Password')";
    if($conn->query($sql)==true){
        $response["success"]=1;
        $response["message"]="Insert thanh cong";
        echo json_encode($response);
        }
        else{
            $response["success"]=0;
            $response["message"]="Loi Insert";
            echo json_encode($response);
        }

}
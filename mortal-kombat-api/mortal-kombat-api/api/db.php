<?php 
$host = 'localhost';
$db_name = 'mortal_kombat';
$username = 'root'; 
$password = ''; 

$conn = new mysqli($host, $username, $password, $db_name);

if ($conn->connect_error) {
    die(json_encode(["message" => "Connection failed: " . $conn->connect_error]));
}

?>
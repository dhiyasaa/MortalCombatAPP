<?php
header("Content-Type: application/json");
include 'db.php';

if (isset($_GET['action'])) {
    switch ($_GET['action']) {
    // KARAKTER
    case 'addKarakter':
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            // Mengambil data selain file
            $nama_karakter = $_POST['nama_karakter'];
    
            // Mengambil data gambar dan suara
            if (isset($_FILES['gambar_karakter']) && isset($_FILES['suara_karakter'])) {
                $gambar_karakter = $_FILES['gambar_karakter']['tmp_name'];
                $suara_karakter = $_FILES['suara_karakter']['tmp_name'];
    
                // Membaca file sebagai binary data
                $gambar_data = file_get_contents($gambar_karakter);
                $suara_data = file_get_contents($suara_karakter);
    
                // Query untuk memasukkan data karakter beserta file gambar dan suara
                $sql = "INSERT INTO karakter (nama_karakter, gambar_karakter, suara_karakter) 
                        VALUES ('$nama_karakter', ?, ?)";
    
                // Prepare statement untuk mencegah SQL injection
                $stmt = $conn->prepare($sql);
                $stmt->bind_param("ss", $gambar_data, $suara_data);
    
                if ($stmt->execute()) {
                    echo json_encode(["message" => "Karakter berhasil ditambahkan."]);
                } else {
                    echo json_encode(["error" => "Error: " . $conn->error]);
                }
            } else {
                echo json_encode(["error" => "File gambar atau suara tidak ditemukan."]);
            }
        }
        break;
    
        case 'getKarakter':
            if ($_SERVER['REQUEST_METHOD'] == 'GET') {
                // Query untuk mengambil data karakter
                $sql = "SELECT * FROM karakter";
                $result = $conn->query($sql);
        
                if ($result->num_rows > 0) {
                    $karakter_list = [];
                    while ($row = $result->fetch_assoc()) {
                        // Encode gambar dan suara dalam base64 agar bisa dikirim dalam JSON
                        $row['gambar_karakter'] = base64_encode($row['gambar_karakter']);
                        $row['suara_karakter'] = base64_encode($row['suara_karakter']);
                        $karakter_list[] = $row;
                    }
                    echo json_encode($karakter_list);
                } else {
                    echo json_encode(["message" => "Tidak ada karakter."]);
                }
            }
            break;
        
            case 'updateKarakter':
                if ($_SERVER['REQUEST_METHOD'] == 'POST') {
                    // Mengambil data dari form-data
                    $id = $_POST['id'];
                    $nama_karakter = $_POST['nama_karakter'];
            
                    // Cek apakah ada file gambar atau suara baru
                    if (isset($_FILES['gambar_karakter']) && isset($_FILES['suara_karakter'])) {
                        $gambar_karakter = $_FILES['gambar_karakter']['tmp_name'];
                        $suara_karakter = $_FILES['suara_karakter']['tmp_name'];
            
                        // Membaca file sebagai binary data
                        $gambar_data = file_get_contents($gambar_karakter);
                        $suara_data = file_get_contents($suara_karakter);
            
                        // Update query dengan gambar dan suara baru
                        $sql = "UPDATE karakter SET nama_karakter = ?, gambar_karakter = ?, suara_karakter = ? WHERE id = ?";
                        $stmt = $conn->prepare($sql);
                        $stmt->bind_param("sssi", $nama_karakter, $gambar_data, $suara_data, $id);
                    } else {
                        // Update query tanpa mengubah gambar atau suara
                        $sql = "UPDATE karakter SET nama_karakter = ? WHERE id = ?";
                        $stmt = $conn->prepare($sql);
                        $stmt->bind_param("si", $nama_karakter, $id);
                    }
            
                    if ($stmt->execute()) {
                        echo json_encode(["message" => "Karakter berhasil diperbarui."]);
                    } else {
                        echo json_encode(["error" => "Error: " . $conn->error]);
                    }
                }
                break;
            
                case 'deleteKarakter':
                    if ($_SERVER['REQUEST_METHOD'] == 'DELETE') {
                        // Mengambil data ID karakter dari body request
                        parse_str(file_get_contents("php://input"), $_DELETE);
                        
                        // Pastikan id ada di body
                        if (isset($_DELETE['id'])) {
                            $id = $_DELETE['id'];
                            
                            // Query untuk menghapus data karakter berdasarkan ID
                            $sql = "DELETE FROM karakter WHERE id = ?";
                            $stmt = $conn->prepare($sql);
                            $stmt->bind_param("i", $id);
                
                            if ($stmt->execute()) {
                                echo json_encode(["message" => "Karakter berhasil dihapus."]);
                            } else {
                                echo json_encode(["error" => "Error: " . $conn->error]);
                            }
                        } else {
                            echo json_encode(["error" => "ID tidak ditemukan."]);
                        }
                    }
                    break;      
        
        // TEKNIK
        case 'addTeknik':
            if ($_SERVER['REQUEST_METHOD'] == 'POST') {
                // Membaca data JSON dari body request
                $input = json_decode(file_get_contents("php://input"), true);
        
                // Memeriksa apakah data yang diperlukan ada
                if (isset($input['karakter_id'], $input['jenis_teknik'], $input['nama_teknik'], $input['teknis'])) {
                    $karakter_id = $input['karakter_id'];
                    $jenis_teknik = $input['jenis_teknik'];
                    $nama_teknik = $input['nama_teknik'];
                    $teknis = $input['teknis'];
        
                    // Validasi input karakter_id harus berupa angka
                    if (!is_numeric($karakter_id) || empty($karakter_id)) {
                        echo json_encode(["error" => "Karakter ID harus berupa angka yang valid."]);
                        exit();
                    }
        
                    // Query untuk memasukkan teknik baru
                    $sql = "INSERT INTO teknik (karakter_id, jenis_teknik, nama_teknik, teknis) 
                            VALUES ('$karakter_id', '$jenis_teknik', '$nama_teknik', '$teknis')";
        
                    if ($conn->query($sql) === TRUE) {
                        echo json_encode(["message" => "Teknik berhasil ditambahkan."]);
                    } else {
                        echo json_encode(["error" => "Error: " . $conn->error]);
                    }
                } else {
                    echo json_encode(["error" => "Semua field (karakter_id, jenis_teknik, nama_teknik, teknis) harus diisi."]);
                }
            }
            break;
        
        case 'getTeknik':
            if ($_SERVER['REQUEST_METHOD'] == 'GET') {
                // Query untuk mendapatkan semua data teknik
                $sql = "SELECT * FROM teknik";
                $result = $conn->query($sql);
        
                if ($result->num_rows > 0) {
                    $teknik_list = [];
                    while ($row = $result->fetch_assoc()) {
                        $teknik_list[] = $row;
                    }
                    echo json_encode($teknik_list);
                } else {
                    echo json_encode(["message" => "Tidak ada data teknik."]);
                }
            }
            break;
            
        case 'updateTeknik':
            if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
                // Membaca data JSON dari body request
                $input = json_decode(file_get_contents("php://input"), true);
        
                // Memeriksa apakah data yang diperlukan ada
                if (isset($input['id'], $input['karakter_id'], $input['jenis_teknik'], $input['nama_teknik'], $input['teknis'])) {
                    $id = $input['id'];
                    $karakter_id = $input['karakter_id'];
                    $jenis_teknik = $input['jenis_teknik'];
                    $nama_teknik = $input['nama_teknik'];
                    $teknis = $input['teknis'];
        
                    // Validasi ID teknik dan karakter_id harus berupa angka
                    if (!is_numeric($id) || !is_numeric($karakter_id) || empty($id) || empty($karakter_id)) {
                        echo json_encode(["error" => "ID dan Karakter ID harus berupa angka yang valid."]);
                        exit();
                    }
        
                    // Query untuk memperbarui data teknik
                    $sql = "UPDATE teknik 
                            SET karakter_id = ?, jenis_teknik = ?, nama_teknik = ?, teknis = ? 
                            WHERE id = ?";
                    $stmt = $conn->prepare($sql);
                    $stmt->bind_param("isssi", $karakter_id, $jenis_teknik, $nama_teknik, $teknis, $id);
        
                    if ($stmt->execute()) {
                        echo json_encode(["message" => "Teknik berhasil diperbarui."]);
                    } else {
                        echo json_encode(["error" => "Error: " . $conn->error]);
                    }
                } else {
                    echo json_encode(["error" => "Semua field (id, karakter_id, jenis_teknik, nama_teknik, teknis) harus diisi."]);
                }
            }
            break;
            
        case 'deleteTeknik':
            if ($_SERVER['REQUEST_METHOD'] == 'DELETE') {
                // Membaca data JSON dari body request
                $input = json_decode(file_get_contents("php://input"), true);
        
                // Memeriksa apakah ID teknik disediakan
                if (isset($input['id'])) {
                    $id = $input['id'];
        
                    // Validasi ID teknik harus berupa angka
                    if (!is_numeric($id) || empty($id)) {
                        echo json_encode(["error" => "ID harus berupa angka yang valid."]);
                        exit();
                    }
        
                    // Query untuk menghapus data teknik berdasarkan ID
                    $sql = "DELETE FROM teknik WHERE id = ?";
                    $stmt = $conn->prepare($sql);
                    $stmt->bind_param("i", $id);
        
                    if ($stmt->execute()) {
                        echo json_encode(["message" => "Teknik berhasil dihapus."]);
                    } else {
                        echo json_encode(["error" => "Error: " . $conn->error]);
                    }
                } else {
                    echo json_encode(["error" => "ID harus diisi."]);
                }
            }
            break;
            
        default:
            echo "Action tidak valid.";
            break;
    }
} else {
    echo "Action tidak ditentukan.";
}
?>

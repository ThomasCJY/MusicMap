<?php
/**
 * Created by PhpStorm.
 * User: thomas
 * Date: 15-5-5
 * Time: 上午9:34
 */


class DB_Functions {

    private $db;
    private $con;

    //put your code here
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->con = $this->db->connect();
    }

    // destructor
    function __destruct() {

    }

    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $email, $password) {
        $uuid = uniqid('', true);
        $query = "INSERT INTO user (unique_id, name, email, password) VALUES('$uuid', '$name', '$email', '$password');";
        $result = mysqli_query($this->con, $query);
        // check for successful store
        if ($result) {
            // get user details
            $uid = mysqli_insert_id($this->con); // last inserted id
            $result = mysqli_query($this->con,"SELECT * FROM user WHERE uid = $uid");
            // return user details
            return mysqli_fetch_array($result);
        } else {
            return false;
        }
    }

    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
        $query = "SELECT * FROM user WHERE email = '$email'";
        $result = mysqli_query($this->con, $query) or die(mysql_error());
        // check for result
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysqli_fetch_array($result);
            $psw_value = $result['password'];
            // check for password equality
            if ($password == $psw_value) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }

    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $result = mysqli_query($this->con, "SELECT email from user WHERE email = '$email'");
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed
            return true;
        } else {
            // user not existed
            return false;
        }
    }

    public function getUserByUUID($uuid){
        $query = "SELECT name, email from user WHERE unique_id = '$uuid';";
        $result = mysqli_query($this->con, $query );
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            return mysqli_fetch_array($result);
        } else {
            // uuid not found
            return false;
        }
    }

    public function getPerformanceByUUID($uuid){
        $query_1 = "SELECT p_id from favourite WHERE uuid = '$uuid';";
        $id_array = mysqli_query($this->con, $query_1);
        $arr = array();

        while($id = mysqli_fetch_array($id_array)){
            $query_2 = "SELECT * from performance WHERE performance_id = ".$id['p_id'].";";
            $data = mysqli_query($this->con, $query_2);
            $data_performance = mysqli_fetch_array($data);

            if($data_performance){
                $place_id = $data_performance['place_id'];
                $query_3 = "SELECT * from place WHERE place_id = ". $place_id. ";";
                $res_place = mysqli_query($this->con, $query_3);
                $data_place = mysqli_fetch_array($res_place);

                $query_4 = "SELECT city_name from city WHERE city_id = ".$data_place['city_id'].";";
                $res_city = mysqli_query($this->con, $query_4);
                $data_city = mysqli_fetch_array($res_city);

                $place = array("pname" => $data_place['place_name'],
                    "lat" => (double)$data_place['lat'], "lng" => (double)$data_place['lng']);
                $list_item = array("place" => $place, "time" => $data_performance['time'],
                    "musician" => $data_performance['musician'],
                    "description" => $data_performance['description'],
                    "city" => $data_city['city_name']);
                array_push($arr, $list_item);
            }
        }

        return $arr;
    }

}

?>
<?php
/**
 * Created by PhpStorm.
 * User: thomas
 * Date: 15-5-5
 * Time: 下午4:12
 */

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "error" => FALSE);

    // check for tag type
    if ($tag == 'profile') {
        // Request type is check profile
        $uuid = $_POST['unique_id'];

        // check for user
        $user = $db->getUserByUUID($uuid);
        if ($user != false) {
            // user found
            $response["error"] = FALSE;
            $response["name"] = $user["name"];
            $response["email"] = $user["email"];
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = TRUE;
            $response["error_msg"] = "Incorrect unique id!";
            echo json_encode($response);
        }
    } elseif ($tag == 'favourite') {

        $uuid = $_POST['unique_id'];
        $arr = $db->getPerformanceByUUID($uuid);

        $result = array("performance" => $arr);

        echo json_encode($result);
    } else {
        // user failed to store
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknown 'tag' value. It should be either 'profile' or 'favourite'";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}


?>
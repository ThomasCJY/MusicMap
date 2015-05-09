<?php
/**
 * Created by PhpStorm.
 * User: thomas
 * Date: 15-5-5
 * Time: 下午4:12
 *
 * This is the php file which deals with get user's
 * profile and favourite information.
 *
 * The format of POST information is:
 * 1, tag:"profile"; unique_id:"$unique_id";
 * 2, tag:"favourite"; unique_id:"$unique_id";
 *
 * The response of the php file is:
 * 1,like tag:"profile"
 * a) error:FALSE; name:"xxxxxx"; email:"x@xx.xxx";
 * b) error:TRUE; error_msg:"xxxxxx";
 *
 * 2,delete tag:"favourite"
 * a) error:FALSE; performance: (performance JSON array)
 * b) error:TRUE; error_msg:"xxxxxx";
 *
 */

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $tag = $_POST['tag'];

    require_once 'DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "error" => FALSE);

    if ($tag == 'profile') {
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
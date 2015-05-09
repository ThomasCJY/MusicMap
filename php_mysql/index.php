<?php
/**
 * Created by PhpStorm.
 * User: thomas
 * Date: 15-5-5
 * Time: 上午9:38
 *
 * This is the php file which deals with Login and Register Activities.
 *
 * The format of POST information is:
 * 1, tag:"login"; email:"x@xx.xxx"; password:(Encrypted password made of 40 hex characters);
 * 2, tag:"Register"; name:"xxxxxx";
 * email:"x@xx.xxx"; password:(Encrypted password made of 40 hex characters);
 *
 * The response of the php file is:
 * 1,Login tag:"login"
 * a) error:FALSE; unique_id:"$uuid"; user:{name:"xxxxxx"; email:"x@xx.xxx"}
 * b) error:TRUE; error_msg:"xxxxxx";
 *
 * 2,Register tag:"register"
 * a) error:FALSE
 * b) error:TRUE; error_msg:"xxxxxx";
 *
 */

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $tag = $_POST['tag'];

    require_once 'DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "error" => FALSE);

    if ($tag == 'login') {
        $email = $_POST['email'];
        $password = $_POST['password'];

        // check for user
        $user = $db->getUserByEmailAndPassword($email, $password);
        if ($user != false) {
            // user found
            $response["error"] = FALSE;
            $response["unique_id"] = $user["unique_id"];
            $response["user"]["name"] = $user["name"];
            $response["user"]["email"] = $user["email"];
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = TRUE;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    } else if ($tag == 'register') {
        // Request type is Register new user
        $name = $_POST['name'];
        $email = $_POST['email'];
        $password = $_POST['password'];

        // check if user is already existed
        if ($db->isUserExisted($email)) {
            // user is already existed - error response
            $response["error"] = TRUE;
            $response["error_msg"] = "User already existed";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeUser($name, $email, $password);
            if ($user) {
                // user stored successfully
                $response["error"] = FALSE;
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    } else {
        // user failed to store
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknow 'tag' value. It should be either 'login' or 'register'";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}
?>
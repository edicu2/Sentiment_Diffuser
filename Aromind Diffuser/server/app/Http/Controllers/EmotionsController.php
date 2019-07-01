<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;

class EmotionsController extends Controller
{
    public function emotionSearch(Request $request) {

        //$value = $request->value;
        $value = preg_replace("/\s+/","",$request->value);
        Log::alert($value);
        //$value = "안녕하세요";
        //$value = "python C:\lavue\back\app\Http\Controllers\ttt.py 안녕하세요~~~~~~~~~~~";
        $command = escapeshellcmd('python C:\lavue\back\app\Http\Controllers\ttt.py ' . $value);
        //$output = shell_exec("python ". $command. $value);
        //$output = shell_exec("python" .$command. $value );
        //$output = exec("python " .$command. "dfdfdfdfdfd" );
        $output = shell_exec($command);
        return $output;

    }
}

<?php

namespace App\Http\Controllers;

use Storage;
use Illuminate\Http\Request;

class DateController extends Controller
{
   /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
     protected $day_temp;
     protected $week_temp;
     protected $month_temp;
     protected $year_temp;

     protected $day_data;
     protected $week_data;
     protected $month_data;
     protected $year_data;
     

     public function __construct()
    {
        $json = Storage::disk('local')->get('feedback.json');
        $json = json_decode($json, true);
        $day_arr = array(0);
        //--Day--
        $start_date = '2019-01-01 00:00:00';//현재시간 적기,date("Y-m-d h:i:s")
        //$end_date = '2019-03-18 15:00:00';//현재시간에서 +4시간
               
        for($i=1; $i<6; $i++){
            $end_date = date("Y-m-d H:i:s", strtotime($start_date." +4 hours"));//+1 month,+1 year,+4 hours
            $test = \DB::table('emotion_values')
                ->select(\DB::raw('round(avg(value),2)'))
                ->whereBetween('check_time',[$start_date, $end_date])
                ->get();
            //$sum = array_sum($test);
            $day_arr[$i] = json_decode($test, true);
            foreach($day_arr[$i] as $j => $v){
              $day_arr[$i] = $v["round(avg(value),2)"];
            }
            $start_date = $end_date;
        }
 
        //$this->day_data = [0.4, 0.2, 0.12, 1.9, 0.1, 0.4, 0.09, 0.8];
        $this->day_data = $day_arr;//$arr
        \Log::info($test);
        $day_avg = (array_sum($this->day_data)/count($this->day_data)); 

        if($day_avg < 0){//음수
            $test_json = $json['bad']['day'];
        }else if($day_avg > 0){//양수
            $test_json = $json['good']['day'];
        }else{//그 외
            $test_json = $json['normal']['day'];
        }
        
        shuffle($test_json);
        
        foreach($test_json as $key => $value){
            $this->day_temp = $value;
            break;
         }

        //--Week--
        $week_arr = array(0);
        for($i=1; $i<8; $i++){
            $end_date = date("Y-m-d H:i:s", strtotime($start_date." +1 days"));//+1 month,+1 year,+4 hours
            $test = \DB::table('emotion_values')
                ->select(\DB::raw('round(avg(value),2)'))
                ->whereBetween('check_time',[$start_date, $end_date])
                ->get();
            //$sum = array_sum($test);
            $week_arr[$i] = json_decode($test, true);
            foreach($week_arr[$i] as $j => $v){
              $week_arr[$i] = $v["round(avg(value),2)"];
            }
            $start_date = $end_date;
        }

        $this->week_data = $week_arr;
        $week_avg = (array_sum($this->week_data)/count($this->week_data)); 

        if($week_avg < 0){
            $test_json = $json['bad']['week'];
        }else if($week_avg > 0){
            $test_json = $json['good']['week'];
        }else{
            $test_json = $json['normal']['week'];
        }
        
        shuffle($test_json);
        
        foreach($test_json as $i => $v){
            $this->week_temp = $v;
            break;
        }

        //--Month--
        $month_arr = array(0);
        for($i=1; $i<13; $i++){
            $end_date = date("Y-m-d H:i:s", strtotime($start_date." +1 month"));//+1 month,+1 year,+4 hours
            $test = \DB::table('emotion_values')
                ->select(\DB::raw('round(avg(value),2)*10'))
                ->whereBetween('check_time',[$start_date, $end_date])
                ->get();
            //$sum = array_sum($test);
            $month_arr[$i] = json_decode($test, true);
            foreach($month_arr[$i] as $j => $v){
              $month_arr[$i] = $v["round(avg(value),2)*10"];
            }
            $start_date = $end_date;
        }

        $this->month_data = $month_arr;
        $month_avg = (array_sum($this->month_data)/count($this->month_data)); 

        if($month_avg < 0){
            $test_json = $json['bad']['month'];
        }else if($month_avg > 0){
            $test_json = $json['good']['month'];
        }else{
            $test_json = $json['normal']['month'];
        }
        
        shuffle($test_json);
        
        foreach($test_json as $i => $v){
            $this->month_temp = $v;
            break;
        }

        //Year
        $year_arr = array(0);
        for($i=1; $i<13; $i++){
            $end_date = date("Y-m-d H:i:s", strtotime($start_date." +1 year"));//+1 month,+1 year,+4 hours
            $test = \DB::table('emotion_values')
                ->select(\DB::raw('round(avg(value),2)'))
                ->whereBetween('check_time',[$start_date, $end_date])
                ->get();
            //$sum = array_sum($test);
            $year_arr[$i] = json_decode($test, true);
            foreach($year_arr[$i] as $j => $v){
              $year_arr[$i] = $v["round(avg(value),2)"];
            }
            $start_date = $end_date;
        }

        $this->year_data = $year_arr;
        $year_avg = (array_sum($this->year_data)/count($this->year_data)); 

        if($year_avg < 0){
            $test_json = $json['bad']['year'];
        }else if($year_avg > 0){
            $test_json = $json['good']['year'];
        }else{
            $test_json = $json['normal']['year'];
        }

        shuffle($test_json);
        
        foreach($test_json as $i => $v){
            $this->year_temp = $v;
            break;
        }
        //$this->exam()

    }   

    public function day()
    {
        
        $data = [
            'labels'=>['', '8h', '12h', '16h', '20h', '24h'],
            'datasets'=>array([
                'borderColor'=>'#F87979',//pie:['#F87979','#F26202','#EAAE00', '#B5CC18']
                'backgroundColor'=>'rgb(255, 0, 0,-1)',
                'borderWidth'=>6,
                'lineTension'=>0,
                'hidden'=>false,
                //'fillColorStart'=> 'rgba(251, 176, 59, 0.25)',
                //'fillColorEnd'=> 'rgba(251, 176, 59, 0)',
                //'pointBorderColor'=> 'red',
                'data'=>$this->day_data
            ]),
            'temp'=>$this->day_temp
        ];
        //\Log::info($arr);
        return response()->json($data, 200);
    }

    public function week(Request $request)
    {
        $week_data = [
            'labels'=>['','월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
            'datasets'=>array([
                'borderColor'=>'#F87979',//pie:['#F87979','#F26202','#EAAE00', '#B5CC18']
                'backgroundColor'=>'rgb(255, 0, 0,-1)',
                'borderWidth'=>6,
                'lineTension'=>0,
                'data'=>$this->week_data,
            ]),
            'temp'=>$this->week_temp
        ];

        return response()->json($week_data, 200);
    }

    public function month(Request $request)
    {
        $month_data = [
            'labels'=>['', '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
            'datasets'=>array([
                'borderColor'=>'#F87979',//pie:['#F87979','#F26202','#EAAE00', '#B5CC18']
                'backgroundColor'=>'rgb(255, 0, 0,-1)',
                'borderWidth'=>6,
                'lineTension'=>0,
                'data'=>$this->month_data,
            ]),
            'temp'=>$this->month_temp
        ];

        return response()->json($month_data, 200);
    }

    public function year(Request $request)
    {
        $year_data = [
            'labels'=>['', '1년', '2년', '3년', '4년', '5년'],
            'datasets'=>array([
                'borderColor'=>'#F87979',//pie:['#F87979','#F26202','#EAAE00', '#B5CC18']
                'backgroundColor'=>'rgb(255, 0, 0,-1)',
                'borderWidth'=>6,
                'lineTension'=>0,
                'data'=>$this->year_data,
            ]),
            'temp'=>$this->year_temp
        ];

        return response()->json($year_data, 200);
    }

    public function recommend(Commend $commend){
        return response()->json($commend, 200);
    }

    //select box에 성별로 정렬하는 부분
    public function woman(){
        return \DB::select("select o.product_code, o.order_code, p.product_name, p.product_img, p.product_price, SUM(o.quantity) AS 'quantity' from orders o JOIN member m JOIN product p
        on(o.id = m.id)
        where substr(m.resident_number,-1) = '1'
        GROUP BY product_code
        order by SUM(o.quantity) DESC
        LIMIT 4;");
    }

    //select box에 성별로 정렬하는 부분
    public function man(){
        return \DB::select("select o.product_code,  p.product_name, p.product_img, p.product_price, SUM(o.quantity)
        AS 'quantity' from orders o JOIN member m JOIN product p
       on(o.id = m.id)
       where substr(m.resident_number,-1) = '1'
       GROUP BY product_code
       order by SUM(o.quantity) DESC
       LIMIT 4;");
    }
}

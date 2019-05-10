<?php

namespace App\Http\Controllers;

use App\Article;
use Illuminate\Http\Request;
use Auth;

class ArticlesController extends Controller
{
    public function index() {
        return Article::orderBy('id', 'desc')->get();
    }

    public function store(Request $request) {

        /* $article = Article::create($request->all());

        return $article; */

        /* $exploded = explode(',', $request->image);

        $decoded = base64_decode($exploded[1]);

        if(str_contains($exploded[0], 'jpeg')) {
            $extension = 'jpg';
        } else {
            $extension = 'png';
        }

        $fileName = str_random().'.'.$extension;

        $path = public_path().'/'.$fileName;

        file_put_contents($path, $decoded);

        $article = Article::create($request->except('image') +
        ['user_id' => '1',
         'image' => $fileName
        ]);

        return $request->all(); */

        if($request->content??"")
        {
            // 커스텀 카드가 있는 경우입니다.
            // 1. 게시글 등록

            DB::table('board')->insert([
            'id'=>'shinhyeonbin',
            'title'=>'게시글 제목입니다.',
            'content'=>$request->content,
            'indate'=>date('Y-m-d')
            ]);

            $sub=DB::table('board')->where('id','shinhyeonbin')->orderByRaw('num DESC')->value('num'); // 게시글을 등록한 사용자의 it에서 최근 게시글 num을 불러와 저장하는 변수입니다.

            // 2. 커스텀 카드 등록록
            DB::table('customcard_board')->insert(
            [
                'num' => $sub,
                'customcard_name' => 'shinhyeonbinCard',
                'user_id' => "shinhyeonbin",
                'positive_perfume' => $request->positive_perfume,
                'positive_strength' => $request->positive_strength,
                'normal_perfume' => $request->normal_perfume,
                'normal_strength' => $request->normal_strength,
                'nagative_perfume' => $request->normal_perfume,
                'nagative_strength' => $request->nagative_strength,
                'rgb' => $request->rgb,
                'bright' => $request->bright
            ]);
            //return $sub;
        } else { // 커스텀 카드 이름이 안 넘어올 경우입니다.
            echo '엘세가 실행됨';
            DB::table('board')->insert([
                'id'=>'shinhyeonbin',
                'title'=>'게시글 제목입니다.',
                'content'=>$request->content,
                'indate' => date('Y-m-d')
            ]);
        }
    }

    public function show($id) {
        $article = Article::find($id);

        /* if(count($product) > 0) {
            return response()->json($product);
        }
 */
        return response()->json($article);

        //return response()->json(['error' => 'Resource not found!'], 404);
    }

    public function update(Request $request, $id) {
        $article = Article::find($id);

        $article->update($request->all());

        return response()->json($article);
    }

    public function destroy($id) {
        try {
            Article::destroy($id);

            return response([], 204);
        } catch (\Exception $e) {
            return response(['Problem deleting the Article', 500]);
        }
    }
}

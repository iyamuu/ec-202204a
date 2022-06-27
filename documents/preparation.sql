-- ユーザー
drop table if exists users cascade;

create table users (
 id serial primary key
 , name varchar(100) not null
 , email varchar(100) not null unique
 , password text not null
 , zipcode varchar(8) not null
 , address varchar(200) not null
 , telephone varchar(15) not null
) ;


--ユーザー登録(pass:morimori)
insert into users(name, email, password, zipcode, address, telephone) values('テストユーザ', 'test@test.co.jp', '$2a$10$Utoo6nr3XIFEh4xOZ9Zr1.n/PtEYBb8HhlLDDklaJwsj.T3uux4kq','111-1111', 'テスト住所', '000-0000-0000');

-- 商品
drop table if exists items cascade;

create table items (
  id integer primary key
  , name text not null
  , description text not null
  , price_m integer not null
  , price_l integer not null
  , image_path text not null
  , deleted boolean default false not null
) ;

insert into items values(1, 'Hawaiianパラダイス', 'ハワイで取れる名産物でかつオーガニックな食料がふんだんに使われたローカルフーズです。健康志向の方に大人気の商品です。', 2160, 3380, '10.jpg');
insert into items values(2, 'アサイーボウル', 'ブラジル発祥のデザートで、アメリカ合衆国のハワイ州で人気が出てよく知られるようになった商品です。アサイーのスムージーをボウルに盛りつけ、グラノーラなどのシリアル食品やバナナやリンゴやイチゴなど好みの果物をのせて蜂蜜をかけて供する。朝食とされることもある。', 1490, 2570, '2.jpg');
insert into items values(3, 'ハワイアンマルゲリータピザ', 'ハワイで取れたフレッシュトマトと野菜、チーズをふんだんに使って作られたマルゲリータピザです。隠し味として中にパイナップルやマンゴーも入っています。', 1490, 2570, '3.jpg');
insert into items values(4, 'カメハメハベーカリーの焼きたてマラサダ', 'ハワイの定番おやつといえば、やっぱりマラサダ。なかでも、カメハメハベーカリーのマラサダは、行列必至 の人気ぶりです。', 1900, 2980, '4.jpg');
insert into items values(5, 'Ray''sのフリフリチキン', 'ノースショア名物の鶏肉を丸焼きにしたフリフリチキンです。現地では土日しか食べられないものを暖かい状態でお届けします。', 1900, 2980, '5.jpg');
insert into items values(6, 'マツモトシェーブアイス', '連日行列ができるノースショアの人気かき氷店のシェーブアイスです。日系人が作ったこの店はノース/ハレイワの観光スポットになっています。', 2700, 4050, '6.jpg');
insert into items values(7, 'レナーズのマラサダGorgeous4', 'ポルトガル発祥のスイーツで有名なドーナツ「マラサダ」。マラサダの代名詞は「レナーズ」と言われるほど有名なお店のマラサダをお届けします。', 2570, 3780, '7.jpg');
insert into items values(8, 'ザ・テラスのエッグベネディクト', 'イングリッシュ・マフィンの半分に、ハム、ベーコンまたはサーモン等や、ポーチドエッグ、オランデーズソースを乗せて作る料理です。', 2160, 3380, '8.jpg');
insert into items values(9, 'アヒポキ丼', 'アヒポキとは、ハワイ語で「アヒ」はまぐろ、「ポキ」は魚を小さく切るという意味のハワイ郷土料理の一つです。 甘辛いタレで絡めてお好みの香味野菜を添えてお召し上がりください。 ', 2700, 4050, '9.jpg');
insert into items values(10, 'ガーリックシュリンプ', 'オアフ島北部のカフク地方で養殖されるエビをにんにく風味の油で炒めた料理。主に移動販売車で売られており、同地方の名物とされる。', 1490, 2570, '1.jpg');
insert into items values(11, 'Bananのソフトクリーム', 'フルーツたっぷりなアイスクリームは、もうリピート間違いなしの絶品もの。冷凍状態のまま鮮度そのままでお届けします。', 2700, 4050, '11.jpg');
insert into items values(12, 'ホノルルコーヒー', '本場のコナ100%のコーヒーです。観光客にはもちろんローカルにも人気で毎日大行列ができます。', 2160, 3380, '12.jpg');
insert into items values(13, 'マカヒキのイルカバナナ', 'ハワイ・アウラニリゾートに入っているマカヒキのブッフェで提供されているイルカの形をしたバナナです。お子様に大人気です。', 2160, 3380, '13.jpg');
insert into items values(14, 'ラムファイヤーのマイタイ', 'シェラトンワイキキ１階に入っているラムファイヤーのマイタイです。お酒ですので20歳以上限定の商品です。', 2980, 4460, '14.jpg');
insert into items values(15, 'ロコモコSpecial', '誰もが大好きロコモコです。こだわりのビーフ100％のハンバーグは、肉感がしっかり残ったステーキのような食べごたえ。', 2440, 3650, '15.jpg');
insert into items values(16, 'ラウラウ', 'タロイモの葉の中央に肉や魚の小片を乗せ、葉の端を葉の中に折り込んで、その上からティの葉で包む。伝統的にはさらにバナナの葉で包まれ、イムと呼ばれる地中のオーブンで、熱い石と一緒に地中に埋めて調理される、ハワイの郷土料理です。', 2700, 4050, '16.jpg');
insert into items values(17, 'ポイ', 'タロイモの球茎から作るポリネシアの主食です。焼くか蒸すかした球茎を、粘りが出るまですりつぶすことによって作られ、絶品の美味しさです。', 2440, 3650, '17.jpg');
insert into items values(18, 'ニコスピア38のアヒステーキ', 'ニコスピアで夜しか提供していないアヒ(マグロ)のステーキです。リピータが多く絶品です。', 2700, 4050, '18.jpg');


-- トッピング
drop table if exists toppings cascade;

create table toppings (
  id integer primary key
  , name text not null
  , price_m integer not null
  , price_l integer not null
) ;

insert into toppings values(1, 'ハワイアンソルト', 200, 300);
insert into toppings values(2, 'ハワイアンマヨネーズ', 200, 300);
insert into toppings values(3, 'ハワイアントマト', 200, 300);
insert into toppings values(4, 'ブルーチーズ', 200, 300);
insert into toppings values(5, 'ハワイアンチョコレート', 200, 300);
insert into toppings values(6, 'アンチョビ', 200, 300);
insert into toppings values(7, 'エビ', 200, 300);
insert into toppings values(8, 'ガーリックスライス', 200, 300);
insert into toppings values(9, 'トロピカルフルーツ', 200, 300);
insert into toppings values(10, 'ハワイ産はちみつ', 200, 300);
insert into toppings values(11, 'ココナッツ', 200, 300);
insert into toppings values(12, 'マンゴー', 200, 300);
insert into toppings values(13, 'パイナップル', 200, 300);
insert into toppings values(14, 'もち', 200, 300);
insert into toppings values(15, 'コーヒー', 200, 300);
insert into toppings values(16, 'スプライト', 200, 300);
insert into toppings values(17, 'ジンジャエール', 200, 300);
insert into toppings values(18, 'コーラ', 200, 300);

-- 注文トッピング
drop table if exists order_toppings cascade;

-- 注文商品
drop table if exists order_items cascade;

-- 注文
drop table if exists orders cascade;

create table orders (
  id serial primary key
  , user_id integer not null
  , status integer not null
  , total_price integer not null
  , order_date date
  , destination_name varchar(100)
  , destination_email varchar(100)
  , destination_zipcode varchar(8)
  , destination_address varchar(200)
  , destination_tel varchar(15)
  , delivery_time timestamp
  , payment_method integer
  ) ;

create table order_items (
  id serial primary key
  , item_id integer not null
  , order_id integer not null
  , quantity integer not null
  , size varchar(1)
  , FOREIGN KEY (order_id) REFERENCES orders (id)
) ;

create table order_toppings (
  id serial primary key
  , topping_id integer not null
  , order_item_id integer not null
  , FOREIGN KEY (order_item_id) REFERENCES order_items (id) on delete cascade
) ;
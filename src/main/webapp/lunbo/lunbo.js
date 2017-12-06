       var Tearchers = [{
             "id": "1",
             "imgsrc": "imges/teach_1.png"
        },
         {
             "id": "2",             
             "imgsrc": "imges/teach_2.png"
         },
         {
             "id": "3",            
             "imgsrc": "imges/teach_3.png"
         }, {
             "id": "4",            
             "imgsrc": "imges/teach_4.png"
         }	];
        //滚动图片对象
        var srcollImages = {
            //将第一个li插在最后一个ul之后
            firstInsertAfterLast: function () {
                $("ul li:first").insertAfter($("ul li:last"));
            },
            /*
                获得高亮显示的li
                $result：获得高亮的li并将高亮显示的li对象返回
              */
            getHightLightLi: function ($container) {
                var $lis = $("ul li", $container);
                var $result = null;
                $lis.each(function (index, element) {
                    if ($(this).attr("class") == "Zzhao_cent") {
                        $result = $(element);
                        $.each(Tearchers, function (index) {
                            //当前对象
                            var teacher = this;
                            if ($result.find("img").attr("src") == teacher.imgsrc) {
                                //因为索引是从0开始，为了让信息和照片对应所做的处理
                                index = index + 1;
                                //对最后一张图片的处理
                                if (index > (Tearchers.length - 1)) {
                                    teacher = Tearchers[0];
                                } else {
                                    teacher = Tearchers[index];
                                }                              
                            }
                        })
                    }
                });
                return $result;
            }
        };
        $(function () {
            //开启定时器
            var timerHandl = timerScroll();
            //鼠标悬停在li和离开li的处理,单击li后的处理
            $("ul li").hover(function () {
                //停止定时器
                clearInterval(timerHandl);
            }, function () {
                //保持句柄
                timerHandl = timerScroll();
            })
            /*
            在滚动的时，单击li并将其移动到中间高亮显示的处理方法
            timerHandl：单击停止计时器所需的计时器句柄
        */
            $("ul li").each(function () {
                $(this).click(function () {
                    var selfclik = $(this);
                    clearInterval(timerHandl);
                    //只对普通的照片进行处理，高亮居中的照片不再处理
                    if ($(this).hasClass("Zzhao")) {
                        //index()方法用来获取jquery对象的位置索引
                        var currentIndex = $(this).index();
                        //高亮图片的索引位置，以中间高亮图片为原点
                        var hightIndex = $(".Zzhao_cent").index();
                        //currentIndex > hightIndex说明单击的图片在高亮图片右边
                        if (currentIndex > hightIndex) {
                            //移动的步数
                            var step = currentIndex - hightIndex;
                            //$(this).removeClass("Zzhao").addClass("Zzhao_cent");
                            //$(this).siblings().removeClass("Zzhao").removeClass("Zzhao_cent").addClass("Zzhao");
                            //移动多少次
                            for (var i = 0; i < step; i++) {
                                srcollImages.firstInsertAfterLast();
                            }

                        } else {
                            //中间高亮图片之前的单击处理
                            //移动的步数
                            var step = currentIndex + hightIndex + 1;
                            //$(this).removeClass("Zzhao").addClass("Zzhao_cent");
                           // $(this).siblings().removeClass("Zzhao").removeClass("Zzhao_cent").addClass("Zzhao");
                            //移动多少次
                            for (var i = 0; i < step; i++) {
                                srcollImages.firstInsertAfterLast();
                            }
                        }
                        //当单击某个图片时，将对应的信息在下面的span中显示
                        for (var i = 0; i < Tearchers.length; i++) {
                            var teacher = Tearchers[i];
                            if (teacher) {
                                if (selfclik.find("img").attr("src") == teacher.imgsrc) {
                                    $(".T1").html(teacher.T1);
                                    $(".T2").html(teacher.T2);
                                    $(".T3").html(teacher.T3);
                                }
                            }

                        }
                    }
                });
            });
        });


        /*
            定时器开始，图片循环滚动
            timerHandl：定时器句柄，用来控制开启和停止*/
        function timerScroll() {
            var timerHandl = setInterval(function () {
                //获得当前高亮的li
                var $hightli = srcollImages.getHightLightLi($(".Teac"));
                //将第一个li插入最后一个li后面
                srcollImages.firstInsertAfterLast();
                //移除高亮的li的Zzhao_cent类，添加遮罩类Zzhao，紧跟的下一个li移除遮罩类Zzhao，添加高亮类Zzhao_cent
                //$hightli.removeClass("Zzhao_cent").addClass("Zzhao").next().removeClass("Zzhao").addClass("Zzhao_cent");

            }, 2000);
            return timerHandl;
        }



/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.impl;

import java.util.Arrays;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.hbasesoft.actsports.portal.service.LuckDrawService;
import com.hbasesoft.framework.cache.core.CacheHelper;
import com.hbasesoft.framework.cache.core.ICache;
import com.hbasesoft.framework.cache.core.annotation.CacheLock;
import com.hbasesoft.framework.cache.core.annotation.Key;
import com.hbasesoft.framework.common.utils.logger.LoggerUtil;
import com.jcraft.jsch.Logger;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年1月5日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.test.lock.impl <br>
 */
@Service
public class LuckDrawServiceImpl implements LuckDrawService {

    private Random random = new Random();

//    private static int level = 2000;

//    private static int[] gifs = new int[] {
//        10, 30, 100
//    };

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param activityCode
     * @param userCode
     * @return <br>
     */
    @Override
    @CacheLock(value = "ShakeActivity", timeOut = 60000, expireTime = 70)
    public int luckDraw(@Key String activityCode, String userCode, int[] gifs, int[] gifsTotal, double probability) {

        int countTotal = 0;
        for (int c : gifsTotal) {
            countTotal += c;
        }
        if (countTotal <= 0) {
            return 0;
        }
        
        int count = 0;
        for (int c : gifs) {
            count += c;
        }
        if (count <= 0) {
            return 0;
        }
        
        int level = (int) (countTotal/probability);

        int winner = getWinner(activityCode, countTotal, level, gifs) + 1;
        // TODO：发放奖品
        if (winner > 0) {
            int index = random.nextInt(count);
            int s = 0;
            for (int i = 0; i < gifs.length; i++) {
                s += gifs[i];
                if (s > index) {
                    gifs[i]--;
                    System.out.println("恭喜获得" + (i + 1) + "等奖，奖品还剩余" + Arrays.toString(gifs));
                    return (i + 1);
                }
            }
        }
        return 0;
    }

    private int getWinner(String activityCode, int count, int level, int[] gifs) {
        ICache cache = CacheHelper.getCache();
        Integer oldLevel = cache.get("LUCK_DRAW_LEVEL", activityCode);
        int key = 0;

        int[] winners;
        if (oldLevel == null || oldLevel - level != 0) {
            winners = new int[count];
            int j = 0;
            while (j < count) {
                int win = random.nextInt(level) + 1;

                boolean flag = false;
                for (int k = 0; k < j; k++) {
                    if (winners[k] == win) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    continue;
                }

                winners[j] = win;
                j++;
            }
            Arrays.sort(winners);
            cache.put("LUCK_DRAW_LEVEL", activityCode, level);
            cache.put("LUCK_DRAW", activityCode, winners);
            LoggerUtil.info("winners = [{0}]",Arrays.toString(winners));
        }
        else {
            winners = cache.get("LUCK_DRAW", activityCode);
            key = CacheHelper.getCache().get("LUCK_DRAW_CURRENT", activityCode);
        }
        CacheHelper.getCache().put("LUCK_DRAW_CURRENT", activityCode, ++key);
        LoggerUtil.info("你是第[{0}]位访问者",key);
        return Arrays.binarySearch(winners, key);
    }
    public static void main(String[] args) {
		String[] win = new String[]{"1", "22", "23", "36", "39"};
		for(int i=0;i<40;i++){
			System.out.println(Arrays.binarySearch(win, i+""));	
		}
	}

}

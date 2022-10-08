package com.example.skydog.dao; /***********************************************************************
 * Module:  ScaleDao.java
 * Author:  new
 * Purpose: Defines the Interface ScaleDao
 ***********************************************************************/



import com.example.skydog.module.entity.Scale;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;
@Mapper
/** @pdOid bab93f3b-d457-47ba-9ae0-613141c84a98 */
public interface ScaleDao {
   /** @param scale
    * @pdOid 1f6498d7-38a9-4051-a5cd-b495bff5de4f */
   void add(Scale scale);
   /** @param scaleId
    * @pdOid 8fd081be-b3de-4590-aa3a-27bb089a4256 */
   void delete(Integer scaleId);
   /** @param scale
    * @pdOid 5a14adeb-a9ca-4b36-a2ba-81940a2c03f2 */
   void update(Scale scale);
   /** @param scaleId
    * @pdOid b05277d6-2722-4d44-996d-b0e380b6d92a */
   Scale queryId(Integer scaleId);
   /** @param scale
    * @pdOid 5ddc1d4e-2fde-46f5-8538-3facedaa8db8 */
   List<Scale> queryCondition(Scale scale);

}
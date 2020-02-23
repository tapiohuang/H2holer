/*
 * Copyright 2018-present, Yudong (Dom) Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hycode.h2holer.common.constants;


public class H2holerConst {
    public static final String HEADER_TOKEN_NAME = "ADANG-AUTH-USER-TOKEN";
    public static final int CLIENT_SERVICE_PORT = 10010;
    public static final int MSG_LEN = 4;
    public static final int MSG_TYPE_LEN = 4;
    public static final int MSG_CLIENT_ID_LEN = 32;
    public static final int MSG_SN_LEN = 32;//包的识别号
    public static final int MSG_MD5_LEN = 32;//数据校验码长度
    public static final int MSG_NO_LEN = 4;
    public static final int COOKIE_MAX_AGE = 7 * 24 * 3600;
    public static final int COOKIE_HALF_HOUR = 30 * 60;
}

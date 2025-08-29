package com.poseidon.IT.support;
import com.poseidon.IT.support.records.TestParams;
import com.poseidon.IT.support.records.TestParamsWithEntity;
import com.poseidon.IT.support.records.TestParamsWithEntityAndParam;
import com.poseidon.IT.support.records.TestParamsWithEntityAndProperty;
import com.poseidon.domain.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TestDataProviders {

    public static Stream<TestParams> entityProviderForGetList() {
        return Stream.of(
                new TestParams("/bidList/list", "bidList/list", "bidLists", "account", "acc1"),
                new TestParams("/curvePoint/list", "curvePoint/list", "curvePoints", "term", 10.0),
                new TestParams("/rating/list", "rating/list", "ratings", "moodysRating", "Aaa"),
                new TestParams("/ruleName/list", "ruleName/list", "ruleNames", "name", "Aaa"),
                new TestParams("/trade/list", "trade/list", "trades", "account", "acc1"),
                new TestParams("/user/list", "user/list", "users", "username", "admin")
        );
    }

    public static Stream<TestParams> entityProviderForAdd() {
        return Stream.of(
                new TestParams("/bidList/add", "bidList/add", "bidListForm", "account", "acc1"),
                new TestParams("/curvePoint/add", "curvePoint/add", "curvePointForm", "term", 10.0),
                new TestParams("/rating/add", "rating/add", "ratingForm", "moodysRating", "Aaa"),
                new TestParams("/ruleName/add", "ruleName/add", "ruleNameForm", "name", "Aaa"),
                new TestParams("/trade/add", "trade/add", "tradeForm", "account", "acc1")
        );
    }

    public static Stream<TestParamsWithEntityAndProperty> entityProviderForShowUpdate() {
        return Stream.of(
                new TestParamsWithEntityAndProperty("/bidList/update", "bidList/update", "bidList",
                        "account", "newUniqueAccount",
                        () -> {
                            BidList b = new BidList();
                            b.setAccount("newUniqueAccount");
                            b.setType("type1");
                            b.setBidQuantity(100.0);
                            return b;
                        }),
                new TestParamsWithEntityAndProperty("/curvePoint/update", "curvePoint/update", "curvePoint",
                        "curveId", 127,
                        () -> {
                            CurvePoint c = new CurvePoint();
                            c.setCurveId(127);
                            c.setTerm(10.0);
                            c.setValue(50.0);
                            return c;
                        }),
                new TestParamsWithEntityAndProperty("/rating/update", "rating/update", "rating",
                        "moodysRating", "newUniqueMoodysRating",
                        () -> {
                            Rating r = new Rating();
                            r.setMoodysRating("newUniqueMoodysRating");
                            r.setSandPRating("SP1");
                            r.setFitchRating("Fitch1");
                            r.setOrderNumber(1);
                            return r;
                        }),
                new TestParamsWithEntityAndProperty("/ruleName/update", "ruleName/update", "ruleName",
                        "name", "NewUniqueName",
                        () -> {
                            RuleName r = new RuleName();
                            r.setName("NewUniqueName");
                            r.setDescription("aaa");
                            r.setJson("json");
                            r.setTemplate("template");
                            r.setSqlPart("sqlPart");
                            r.setSqlStr("sqlStr");
                            return r;
                        }),
                new TestParamsWithEntityAndProperty("/trade/update", "trade/update", "trade",
                        "account", "newUniqueAccount",
                        () -> {
                            Trade t = new Trade();
                            t.setAccount("newUniqueAccount");
                            t.setType("type");
                            t.setBuyQuantity(100.0);
                            t.setSellQuantity(100.0);
                            return t;
                        }),
                new TestParamsWithEntityAndProperty("/user/update", "user/update", "user",
                        "username", "newUniqueUsername",
                        () -> {
                            User u = new User();
                            u.setUsername("newUniqueUsername");
                            u.setFullname("Test User");
                            u.setPassword("NewPassword02!");
                            u.setRole("USER");
                            return u;
                        })

        );
    }


    public static Stream<TestParamsWithEntityAndParam> entityProviderForAddValidate() {
        return Stream.of(
                new TestParamsWithEntityAndParam("/bidList/validate", "/bidList/list",
                        () -> {
                            BidList b = new BidList();
                            b.setAccount("newUniqueAccount");
                            b.setType("type1");
                            b.setBidQuantity(100.0);
                            return b;
                        },
                        entity -> {
                            BidList b = (BidList) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("account", b.getAccount());
                            map.put("type", b.getType());
                            map.put("bidQuantity", String.valueOf(b.getBidQuantity()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam("/curvePoint/validate", "/curvePoint/list",
                        () -> {
                            CurvePoint c = new CurvePoint();
                            c.setCurveId(127);
                            c.setTerm(10.0);
                            c.setValue(50.0);
                            return c;
                        },
                        entity -> {
                            CurvePoint c = (CurvePoint) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("curveId", String.valueOf(c.getCurveId()));
                            map.put("term", String.valueOf(c.getTerm()));
                            map.put("value", String.valueOf(c.getValue()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam("/rating/validate", "/rating/list",
                        () -> {
                            Rating r = new Rating();
                            r.setMoodysRating("newUniqueMoodysRating");
                            r.setSandPRating("SP1");
                            r.setFitchRating("Fitch1");
                            r.setOrderNumber(1);
                            return r;
                        },
                        entity -> {
                            Rating c = (Rating) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("moodysRating", String.valueOf(c.getMoodysRating()));
                            map.put("sandPRating", String.valueOf(c.getSandPRating()));
                            map.put("fitchRating", String.valueOf(c.getFitchRating()));
                            map.put("orderNumber", String.valueOf(c.getOrderNumber()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam("/ruleName/validate", "/ruleName/list",
                        () -> {
                            RuleName r = new RuleName();
                            r.setName("NewUniqueName");
                            r.setDescription("aaa");
                            r.setJson("json");
                            r.setTemplate("template");
                            r.setSqlPart("sqlPart");
                            r.setSqlStr("sqlStr");
                            return r;
                        },
                        entity -> {
                            RuleName c = (RuleName) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("name", String.valueOf(c.getName()));
                            map.put("description", String.valueOf(c.getDescription()));
                            map.put("json", String.valueOf(c.getJson()));
                            map.put("template", String.valueOf(c.getTemplate()));
                            map.put("sqlPart", String.valueOf(c.getSqlPart()));
                            map.put("sqlStr", String.valueOf(c.getSqlStr()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam("/trade/validate", "/trade/list",
                        () -> {
                            Trade t = new Trade();
                            t.setAccount("newUniqueAccount");
                            t.setType("type");
                            t.setBuyQuantity(100.0);
                            t.setSellQuantity(100.0);
                            return t;
                        },
                        entity -> {
                            Trade c = (Trade) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("account", String.valueOf(c.getAccount()));
                            map.put("type", String.valueOf(c.getType()));
                            map.put("buyQuantity", String.valueOf(c.getBuyQuantity()));
                            map.put("sellQuantity", String.valueOf(c.getSellQuantity()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam("/user/validate", "/user/list",
                        () -> {
                            User u = new User();
                            u.setUsername("newUniqueUsername");
                            u.setFullname("Test User");
                            u.setPassword("NewPassword02!");
                            u.setRole("USER");
                            return u;
                        },
                        entity -> {
                            User c = (User) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("username", String.valueOf(c.getUsername()));
                            map.put("fullname", String.valueOf(c.getFullname()));
                            map.put("password", String.valueOf(c.getPassword()));
                            map.put("role", String.valueOf(c.getRole()));
                            return map;
                        }
                )
        );
    }

    public static Stream<TestParamsWithEntityAndParam> entityProviderForUpdateValidate() {
        return Stream.of(
                new TestParamsWithEntityAndParam(
                        "/bidList/update",
                        "/bidList/list",
                        () -> {
                            BidList b = new BidList();
                            b.setAccount("updatedAccount");
                            b.setType("updatedType");
                            b.setBidQuantity(123.0);
                            return b;
                        },
                        entity -> {
                            BidList b = (BidList) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("account", b.getAccount());
                            map.put("type", b.getType());
                            map.put("bidQuantity", String.valueOf(b.getBidQuantity()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam(
                        "/curvePoint/update",
                        "/curvePoint/list",
                        () -> {
                            CurvePoint c = new CurvePoint();
                            c.setCurveId(127);
                            c.setTerm(10.0);
                            c.setValue(50.0);
                            return c;
                        },
                        entity -> {
                            CurvePoint c = (CurvePoint) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("curveId", String.valueOf(c.getCurveId()));
                            map.put("term", String.valueOf(c.getTerm()));
                            map.put("value", String.valueOf(c.getValue()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam(
                        "/rating/update",
                        "/rating/list",
                        () -> {
                            Rating r = new Rating();
                            r.setMoodysRating("updateUniqueMoodysRating");
                            r.setSandPRating("SP1");
                            r.setFitchRating("Fitch1");
                            r.setOrderNumber(1);
                            return r;
                        },
                        entity -> {
                            Rating c = (Rating) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("moodysRating", String.valueOf(c.getMoodysRating()));
                            map.put("sandPRating", String.valueOf(c.getSandPRating()));
                            map.put("fitchRating", String.valueOf(c.getFitchRating()));
                            map.put("orderNumber", String.valueOf(c.getOrderNumber()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam(
                        "/ruleName/update",
                        "/ruleName/list",
                        () -> {
                            RuleName r = new RuleName();
                            r.setName("updateUniqueName");
                            r.setDescription("aaa");
                            r.setJson("json");
                            r.setTemplate("template");
                            r.setSqlPart("sqlPart");
                            r.setSqlStr("sqlStr");
                            return r;
                        },
                        entity -> {
                            RuleName c = (RuleName) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("name", String.valueOf(c.getName()));
                            map.put("description", String.valueOf(c.getDescription()));
                            map.put("json", String.valueOf(c.getJson()));
                            map.put("template", String.valueOf(c.getTemplate()));
                            map.put("sqlPart", String.valueOf(c.getSqlPart()));
                            map.put("sqlStr", String.valueOf(c.getSqlStr()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam(
                        "/trade/update",
                        "/trade/list",
                        () -> {
                            Trade t = new Trade();
                            t.setAccount("updateUniqueAccount");
                            t.setType("type");
                            t.setBuyQuantity(100.0);
                            t.setSellQuantity(100.0);
                            return t;
                        },
                        entity -> {
                            Trade c = (Trade) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("account", String.valueOf(c.getAccount()));
                            map.put("type", String.valueOf(c.getType()));
                            map.put("buyQuantity", String.valueOf(c.getBuyQuantity()));
                            map.put("sellQuantity", String.valueOf(c.getSellQuantity()));
                            return map;
                        }
                ),
                new TestParamsWithEntityAndParam(
                        "/user/update",
                        "/user/list",
                        () -> {
                            User u = new User();
                            u.setUsername("newUniqueUsername29232");
                            u.setFullname("Test User");
                            u.setPassword("NewPassword02!");
                            u.setRole("USER");
                            return u;
                        },
                        entity -> {
                            User c = (User) entity;
                            Map<String,String> map = new HashMap<>();
                            map.put("username", String.valueOf(c.getUsername()));
                            map.put("fullname", String.valueOf(c.getFullname()));
                            map.put("role", String.valueOf(c.getRole()));
                            map.put("password", String.valueOf(c.getPassword()));
                            return map;
                        }
                )
        );
    }

    public static Stream<TestParamsWithEntity> entityProviderForDelete() {
        return Stream.of(
                new TestParamsWithEntity(
                        "/bidList/delete",
                        "/bidList/list",
                        () -> {
                            BidList b = new BidList();
                            b.setAccount("updatedAccount");
                            b.setType("updatedType");
                            b.setBidQuantity(123.0);
                            return b;
                        }
                ),
                new TestParamsWithEntity(
                        "/curvePoint/delete",
                        "/curvePoint/list",
                        () -> {
                            CurvePoint c = new CurvePoint();
                            c.setCurveId(127);
                            c.setTerm(10.0);
                            c.setValue(50.0);
                            return c;
                        }
                ),
                new TestParamsWithEntity(
                        "/rating/delete",
                        "/rating/list",
                        () -> {
                            Rating r = new Rating();
                            r.setMoodysRating("updateUniqueMoodysRating");
                            r.setSandPRating("SP1");
                            r.setFitchRating("Fitch1");
                            r.setOrderNumber(1);
                            return r;
                        }
                ),
                new TestParamsWithEntity(
                        "/ruleName/delete",
                        "/ruleName/list",
                        () -> {
                            RuleName r = new RuleName();
                            r.setName("updateUniqueName");
                            r.setDescription("aaa");
                            r.setJson("json");
                            r.setTemplate("template");
                            r.setSqlPart("sqlPart");
                            r.setSqlStr("sqlStr");
                            return r;
                        }
                ),
                new TestParamsWithEntity(
                        "/trade/delete",
                        "/trade/list",
                        () -> {
                            Trade t = new Trade();
                            t.setAccount("updateUniqueAccount");
                            t.setType("type");
                            t.setBuyQuantity(100.0);
                            t.setSellQuantity(100.0);
                            return t;
                        }
                ),
                new TestParamsWithEntity(
                        "/user/delete",
                        "/user/list",
                        () -> {
                            User u = new User();
                            u.setUsername("newUniqueUsername29232");
                            u.setFullname("Test User");
                            u.setPassword("NewPassword02!");
                            u.setRole("USER");
                            return u;
                        }
                )
        );
    }

}

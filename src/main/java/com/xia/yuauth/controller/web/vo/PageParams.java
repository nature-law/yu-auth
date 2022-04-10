package com.xia.yuauth.controller.web.vo;

import com.xia.yuauth.common.enums.Direction;

import java.util.List;
import java.util.StringJoiner;

/**
 * description: 分页参数
 *
 * @author wanghaoxin
 * date     2021/12/5 21:45
 * @version 1.0
 */
public class PageParams<T> {

    private T params;

    private Integer pageNum;
    private Integer pageSize;

    private Sort sort;

    public PageParams() {
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    /**
     * Sort option for queries. You have to provide at least a list of properties to sort for that must not include
     * {@literal null} or empty strings. The direction defaults to {@link org.springframework.data.domain.Sort#DEFAULT_DIRECTION}.
     *
     * @author Oliver Gierke
     * @author Thomas Darimont
     * @author Mark Paluch
     */
    public static class Sort {
        public Sort() {
        }

        private List<Sort.Order> orders;

        public List<Order> getOrders() {
            return orders;
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }

        /**
         * Enumeration for null handling hints that can be used in {@link org.springframework.data.domain.Sort.Order} expressions.
         *
         * @author Thomas Darimont
         * @since 1.8
         */
        public static enum NullHandling {

            /**
             * Lets the data store decide what to do with nulls.
             */
            NATIVE,

            /**
             * A hint to the used data store to order entries with null values before non null entries.
             */
            NULLS_FIRST,

            /**
             * A hint to the used data store to order entries with null values after non null entries.
             */
            NULLS_LAST;
        }

        /**
         * PropertyPath implements the pairing of an {@link org.springframework.data.domain.Sort.Direction} and a property. It is used to provide input for
         * {@link org.springframework.data.domain.Sort}
         *
         * @author Oliver Gierke
         * @author Kevin Raymond
         */
        public static class Order {
            private Direction direction;
            private String property;
            private boolean ignoreCase;
            private NullHandling nullHandling;

            public Order() {
            }

            public Direction getDirection() {
                return direction;
            }

            public void setDirection(Direction direction) {
                this.direction = direction;
            }

            public String getProperty() {
                return property;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public boolean isIgnoreCase() {
                return ignoreCase;
            }

            public void setIgnoreCase(boolean ignoreCase) {
                this.ignoreCase = ignoreCase;
            }

            public NullHandling getNullHandling() {
                return nullHandling;
            }

            public void setNullHandling(NullHandling nullHandling) {
                this.nullHandling = nullHandling;
            }
        }
    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String getSortString() {
        List<Sort.Order> orders = this.getSort().getOrders();
        StringJoiner joiner = new StringJoiner(",");
        for (Sort.Order order : orders) {
            joiner.add(order.getProperty() + " " + order.getDirection().name());
        }
        return joiner.toString();
    }
}

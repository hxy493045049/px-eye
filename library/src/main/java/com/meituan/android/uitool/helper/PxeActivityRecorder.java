package com.meituan.android.uitool.helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.meituan.android.uitool.base.activity.PxeBaseActivity;
import com.meituan.android.uitool.utils.PxeActivityUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/10/16 on 下午6:10
 * 维护两个链表,分别记录functionAct和普通activity
 */
public class PxeActivityRecorder {
    private LinkedActivityRecord commonActRecord = new LinkedActivityRecord(true);//普通的activity,排除pxe功能
    private LinkedActivityRecord functionActRecord = new LinkedActivityRecord(true);//pxe功能的activity
    private WeakReference<Activity> targetActivityRef;


    public void setTargetActivity(Activity targetActivityRef) {
        this.targetActivityRef = new WeakReference<>(targetActivityRef);
    }

    @Nullable
    public Activity getTargetActivity() {
        Activity act;
        if (targetActivityRef != null &&
                (act = targetActivityRef.get()) != null &&
                !PxeActivityUtils.isActivityInvalid(act)) {
            return act;
        }
        return null;
    }

    public static PxeActivityRecorder getInstance() {
        return Holder.instance;
    }

    public void release() {
        if (targetActivityRef != null) {
            targetActivityRef.clear();
            targetActivityRef = null;
        }
        commonActRecord.next = null;
        functionActRecord.next = null;
    }

    public Activity getTopActivity(boolean isPop) {
        return commonActRecord.getTopActivity(isPop);
    }

    public Activity getFunctionTopActivity(boolean isPop) {
        return functionActRecord.getTopActivity(isPop);
    }

    public List<Activity> getFunctionActivities() {
        return functionActRecord.getActivities();
    }

    public void recordActivity(Activity act) {
        if (!PxeActivityUtils.isActivityInvalid(act)) {
            if (act instanceof PxeBaseActivity) {
                functionActRecord.addRecord(act);
            } else {
                commonActRecord.addRecord(act);
            }
        }
    }

    public void removeActivity(Activity act) {
        if (!PxeActivityUtils.isActivityInvalid(act)) {
            if (act instanceof PxeBaseActivity) {
                functionActRecord.removeActivity(act);
            } else {
                commonActRecord.removeActivity(act);
            }
        }
    }

    //-------------------private-------------------------------

    private PxeActivityRecorder() {
    }

    private static class Holder {
        private static PxeActivityRecorder instance = new PxeActivityRecorder();
    }

    private static class LinkedActivityRecord {
        private LinkedActivityRecord(boolean head) {
            this.isHead = head;
        }

        private LinkedActivityRecord() {

        }

        private LinkedActivityRecord next;
        private LinkedActivityRecord pre;
        private WeakReference<Activity> actRef;
        private boolean isHead;

        private boolean hasNext() {
            return next != null;
        }

        private void addRecord(Activity act) {
            if (!PxeActivityUtils.isActivityInvalid(act)) {
                LinkedActivityRecord nextRecord = new LinkedActivityRecord();
                nextRecord.actRef = new WeakReference<>(act);
                nextRecord.pre = this;
                this.next = nextRecord;
            }
        }

        @NonNull
        private LinkedActivityRecord getEndRecord() {
            LinkedActivityRecord end = this;
            while (end.hasNext()) {
                //过滤无效的act节点
                if (PxeActivityUtils.isActivityInvalid(end.next.actRef.get())) {
                    removeNode(end.next);
                }
                if (end.hasNext()) {
                    end = end.next;
                }
            }
            return end;
        }

        @Nullable
        private Activity getTopActivity(boolean isPop) {
            LinkedActivityRecord end = getEndRecord();
            if (end.isHead) {
                return null;
            } else {
                if (isPop) {
                    end.pre.next = null;
                    end.pre = null;
                }
                return end.actRef.get();
            }
        }

        private List<Activity> getActivities() {
            List<Activity> activities = new ArrayList<>();
            //第一个是头结点,没有内容
            LinkedActivityRecord node = this;
            while (node.hasNext()) {
                node = node.next;
                Activity act = node.actRef.get();
                if (!PxeActivityUtils.isActivityInvalid(act)) {
                    activities.add(act);
                }
            }
            return activities;
        }

        private void removeActivity(Activity act) {
            LinkedActivityRecord end = getEndRecord();
            while (!end.isHead) {
                if (act == end.actRef.get()) {
                    removeNode(end);
                    return;
                }
                end = end.pre;
            }
        }

        private void removeNode(LinkedActivityRecord node) {
            node.pre.next = node.next;
            if (node.next != null) {
                node.next.pre = node.pre;
            }
        }
    }
}

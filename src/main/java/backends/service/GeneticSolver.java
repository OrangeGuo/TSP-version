package backends.service;

import backends.model.Adapt;
import backends.util.Caches;
import backends.model.City;
import backends.util.Config;
import backends.model.AbstractSolver;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class GeneticSolver extends AbstractSolver {
    List<List<Integer>> population = new ArrayList<>();//种群
    List<Adapt> allAdapts = Lists.newArrayList();//记录种群适应度
    Caches caches;
    int populationNum;//种群个体数
    double copyRate;//最优个体复制率
    int cityNum;
    Adapt bestAdapt;

    @Override
    public void process() {
        initPopulation();
        for (int i = 0; i < Config.generations; i++) {
            this.updateAdapt();
            this.copy();
            this.mix();
            this.updateAdapt();
            this.copy();
            this.change();
            this.explode();
        }
        Config.shortestDistance = bestAdapt.getDistance();
        Config.shortestPath.clear();
        Config.shortestPath.addAll(bestAdapt.getIndividual());
    }

    //种群初始化
    public void initPopulation() {
        populationNum = Config.populationNum;//种群个体数
        copyRate = Config.copyRate;//最优个体复制率
        cityNum = Config.cities.size();
        caches = new Caches();
        List<Integer> standardIndividual = Lists.newArrayList();
        for (int i = 0; i < cityNum; i++) {
            standardIndividual.add(i + 1);
        }
        for (int i = 0; i < populationNum; i++) {
            Collections.shuffle(standardIndividual);
            population.add(new ArrayList<>(standardIndividual));
        }
    }

    //计算种群适应度
    public void updateAdapt() {
        allAdapts.clear();
        for (int i = 0; i < population.size(); i++) {
            //分别取出每个个体计算适应度
            double distance_adapt = this.calIndividualDistance(population.get(i));
            Adapt newAdapt = Adapt.builder().individual(new ArrayList<>(population.get(i))).distance(distance_adapt).build();
            allAdapts.add(newAdapt);
        }
    }

    public double calIndividualDistance(List<Integer> li) {
        double distance = 0;
        for (int i = 0; i < li.size() - 1; i++) {
            City city = caches.getCityByNo(li.get(i));
            City nextCity = caches.getCityByNo(li.get(i + 1));
            distance += Math.sqrt(caches.getDistanceByCity(city, nextCity));
        }
        City start = caches.getCityByNo(li.get(0));
        City end = caches.getCityByNo(li.get(li.size() - 1));
        distance += Math.sqrt(caches.getDistanceByCity(start, end));
        return distance;
    }

    //复制
    public void copy() {
        Adapt currentBestAdapt = allAdapts.stream().min(Comparator.comparing(Adapt::getDistance)).get();
        if (bestAdapt == null || currentBestAdapt.getDistance() < bestAdapt.getDistance()) {
            bestAdapt = Adapt.builder()
                    .individual(currentBestAdapt.getIndividual())
                    .distance(currentBestAdapt.getDistance())
                    .build();
        }
        for (int i = 0; i < populationNum * (copyRate); i++) {
            List<Integer> newlList = new ArrayList<>(bestAdapt.getIndividual());
            population.add(0, newlList);
            Adapt adapt = Adapt.builder()
                    .individual(newlList)
                    .distance(bestAdapt.getDistance())
                    .build();
            allAdapts.add(0, adapt);
        }
        population.clear();
        allAdapts = allAdapts.stream().sorted(Comparator.comparing(Adapt::getDistance)).collect(Collectors.toList());
        allAdapts = allAdapts.subList(0, populationNum);
        population = allAdapts.stream().map(Adapt::getIndividual).collect(Collectors.toList());
        populationNum = population.size();
    }

    //交叉
    public void mix() {
        int size = cityNum;//单个个体基因总数
        int a = -2, b = -1;//交叉步长
        while (b < populationNum) {
            a += 2;
            b += 2;

            if (b >= populationNum) break;
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            while (x == y) {
                y = (int) (Math.random() * size);
            }
            List<Integer> li1 = population.get(a);
            List<Integer> li2 = population.get(b);

            //开始交叉
            int temp = li1.get(x);
            li1.set(x, li1.get(y));
            li1.set(y, temp);
            temp = li2.get(x);
            li2.set(x, li2.get(y));
            li2.set(y, temp);


        }

    }

    public void compareAndSwap(int a, int b, List<Integer> list, double distance) {
        List<Integer> tempList = new ArrayList<>(list);
        int temp = tempList.get(a);
        tempList.set(a, tempList.get(b));
        tempList.set(b, temp);
        if (calIndividualDistance(list) < distance) {
            temp = list.get(a);
            list.set(a, list.get(b));
            list.set(b, temp);
        }
    }
    //变异

    public void change() {
        for (int i = 0; i < populationNum; i++) {
            //随机产生交叉基因片段
            int a = (int) (Math.random() * cityNum);
            if (a == cityNum - 1) a--;

            //开始变异
            List<Integer> lix = population.get(i);
            int temp = lix.get(a);
            lix.set(a, lix.get(a + 1));
            lix.set(a + 1, temp);
        }
    }

    public void explode() {
        for (int i = 0; i < populationNum; i++) {

            List<Integer> individual = population.get(i);
            //PathList wList=new PathList(lix);
            //population.add(wList);
            //随机产生交叉基因片段
            double averageDistance = allAdapts.get(i).getDistance() / cityNum;
            for (int j = 0; j < cityNum - 1; j++) {
                if (Math.sqrt(caches.getDistanceByCity(caches.getCityByNo(individual.get(j)), caches.getCityByNo(individual.get(j + 1)))) > averageDistance) {
                    //开始变异
                    int temp = individual.get(j);
                    individual.set(j, individual.get(j + 1));
                    individual.set(j + 1, temp);
                    break;
                }
            }
        }
    }


}

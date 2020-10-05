package backends.service;

import backends.Adapt;
import backends.Caches;
import backends.City;
import backends.Config;
import backends.model.AbstractSolver;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticSolver extends AbstractSolver {
    List<List<Integer>> population = new ArrayList<>();//种群
    List<Adapt> allAdapts = Lists.newArrayList();//记录种群适应度
    Caches caches = new Caches();
    int N = Config.populationNum;//种群个体数
    double rate_best = Config.copyRate;//最优个体复制率
    double rate_mix = 0;//交叉率
    int cityNum=Config.cities.size();
    List<City> cities=new ArrayList<>(Config.cities);
    Adapt bestAdapt;

    @Override
    public void process() {
        initPopulation(Config.populationNum);
        for (int i = 0; i < Config.generations; i++) {
            this.updateAdapt();
            this.copy();
            this.mix();
            this.updateAdapt();
            this.copy();
            this.change();
        }
        Config.shortestDistance=bestAdapt.getDistance();
        Config.shortestPath.clear();
        Config.shortestPath.addAll(bestAdapt.getIndividual());
    }

    //种群初始化
    public void initPopulation(int num) {
        for (int i = 0; i < num; i++) {
            population.add(initIndividual());
        }
    }

    public List<Integer> initIndividual() {
        Set<Integer> individual = Sets.newHashSet();
        while (individual.size() < cityNum) {
            individual.add(ThreadLocalRandom.current().nextInt(1,cityNum+1));
        }
        return new ArrayList<>(individual);
    }

    //计算种群适应度
    public void updateAdapt() {
        allAdapts.clear();
        for (int i = 0; i < population.size(); i++) {
            //分别取出每个个体计算适应度
            double distance_adapt = this.getdistance(population.get(i));
            Adapt newAdapt = Adapt.builder().individual(new ArrayList<>(population.get(i))).distance(distance_adapt).build();
            allAdapts.add(newAdapt);
        }
    }

    public double getdistance(List<Integer> li) {
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
        Adapt currentBestAdapt = Adapt.builder().distance(Double.MAX_VALUE).build();
        for (Adapt adapt : allAdapts) {
            if (adapt.getDistance() < currentBestAdapt.getDistance()) {
                currentBestAdapt = adapt;
            }
        }
        if (bestAdapt == null || currentBestAdapt.getDistance() < bestAdapt.getDistance()) {
            bestAdapt = Adapt.builder()
                    .individual(currentBestAdapt.getIndividual())
                    .distance(currentBestAdapt.getDistance())
                    .build();
        }


        for (int i = 0; i < N * (rate_best); i++) {
            List<Integer> newlList = new ArrayList<>(bestAdapt.getIndividual());
            population.add(0, newlList);
            Adapt adapt = Adapt.builder()
                    .individual(newlList)
                    .distance(bestAdapt.getDistance())
                    .build();
            allAdapts.add(0, adapt);
        }
        while (population.size() > N) {
            double max = 0;
            int index = 0;
            for (int i = 0; i < allAdapts.size(); i++) {
                Adapt nAdapt = allAdapts.get(i);

                if (max < nAdapt.getDistance()) {
                    max = nAdapt.getDistance();

                    index = i;

                }
            }
            population.remove(index);
            allAdapts.remove(index);
        }
        N = population.size();
    }

    //交叉
    public void mix() {
        int size = cityNum;//单个个体基因总数
        int a = -2, b = -1;//交叉步长
        while (b < N) {
            a += 2;
            b += 2;

            if (b >= N) break;
            if (true) {
                int x = (int) (Math.random() * size);
                int y = (int) (Math.random() * size);
                while (x == y) {
                    y = (int) (Math.random() * size);
                }
                List<Integer> li1 = population.get(a);
                List<Integer> li2 = population.get(b);
                //PathList w1=new PathList(li1);
                //PathList w2=new PathList(li2);
                //population.add(w1);
                //population.add(w2);
                //交叉点位

                //开始交叉
                int temp = li1.get(x);
                li1.set(x, li1.get(y));
                li1.set(y, temp);
                temp = li2.get(x);
                li2.set(x, li2.get(y));
                li2.set(y, temp);

            }


        }

    }
    //变异

    public void change() {
        for (int i = 0; i < N; i++) {

            List<Integer> lix = population.get(i);
            //PathList wList=new PathList(lix);
            //population.add(wList);
            //随机产生交叉基因片段
            int a = (int) (Math.random() * cityNum);
            if (a == cityNum - 1) a--;

            //开始变异
            int temp = lix.get(a);
            lix.set(a, lix.get(a + 1));
            lix.set(a + 1, temp);
        }
    }

    public void explode() {
        for (int i = 0; i < N; i++) {

            List<Integer> lix = population.get(i);
            //PathList wList=new PathList(lix);
            //population.add(wList);
            //随机产生交叉基因片段
            double di = allAdapts.get(i).getDistance() / cityNum;
            for (int j = 0; j < cityNum - 1; j++) {
                if (this.betwwenpoints(j, j + 1) > di) {
                    //开始变异
                    int temp = lix.get(j);
                    lix.set(j, lix.get(j + 1));
                    lix.set(j + 1, temp);
                    break;
                }


            }

        }
    }

    public double betwwenpoints(int m, int n) {
        return Math.sqrt(caches.getDistanceByCity(cities.get(n), cities.get(m)));
    }

}

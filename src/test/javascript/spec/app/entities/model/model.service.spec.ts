import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ModelService } from 'app/entities/model/model.service';
import { IModel, Model } from 'app/shared/model/model.model';

describe('Service Tests', () => {
  describe('Model Service', () => {
    let injector: TestBed;
    let service: ModelService;
    let httpMock: HttpTestingController;
    let elemDefault: IModel;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ModelService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Model(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            insertTs: currentDate.format(DATE_TIME_FORMAT),
            lastUpdateTs: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Model', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            insertTs: currentDate.format(DATE_TIME_FORMAT),
            lastUpdateTs: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            insertTs: currentDate,
            lastUpdateTs: currentDate
          },
          returnedFromService
        );
        service
          .create(new Model(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Model', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            mailNetworkName: 'BBBBBB',
            insertTs: currentDate.format(DATE_TIME_FORMAT),
            lastUpdateTs: currentDate.format(DATE_TIME_FORMAT),
            activated: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            insertTs: currentDate,
            lastUpdateTs: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Model', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            mailNetworkName: 'BBBBBB',
            insertTs: currentDate.format(DATE_TIME_FORMAT),
            lastUpdateTs: currentDate.format(DATE_TIME_FORMAT),
            activated: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            insertTs: currentDate,
            lastUpdateTs: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Model', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

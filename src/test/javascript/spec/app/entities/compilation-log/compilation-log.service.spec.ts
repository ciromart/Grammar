import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CompilationLogService } from 'app/entities/compilation-log/compilation-log.service';
import { ICompilationLog, CompilationLog } from 'app/shared/model/compilation-log.model';

describe('Service Tests', () => {
  describe('CompilationLog Service', () => {
    let injector: TestBed;
    let service: CompilationLogService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompilationLog;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CompilationLogService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CompilationLog(0, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            insertTs: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatTs: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a CompilationLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            insertTs: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatTs: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            insertTs: currentDate,
            lastUpdatTs: currentDate
          },
          returnedFromService
        );
        service
          .create(new CompilationLog(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CompilationLog', () => {
        const returnedFromService = Object.assign(
          {
            insertTs: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatTs: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            rpkLink: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            insertTs: currentDate,
            lastUpdatTs: currentDate
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

      it('should return a list of CompilationLog', () => {
        const returnedFromService = Object.assign(
          {
            insertTs: currentDate.format(DATE_TIME_FORMAT),
            lastUpdatTs: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            rpkLink: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            insertTs: currentDate,
            lastUpdatTs: currentDate
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

      it('should delete a CompilationLog', () => {
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
